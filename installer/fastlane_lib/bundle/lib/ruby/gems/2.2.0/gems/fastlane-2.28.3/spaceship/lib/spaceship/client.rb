require 'faraday' # HTTP Client
require 'faraday-cookie_jar'
require 'faraday_middleware'
require 'fastlane/version'
require 'logger'
require 'spaceship/babosa_fix'
require 'spaceship/helper/net_http_generic_request'
require 'spaceship/helper/plist_middleware'
require 'spaceship/ui'
require 'tmpdir'
require 'cgi'

Faraday::Utils.default_params_encoder = Faraday::FlatParamsEncoder

if ENV["SPACESHIP_DEBUG"]
  require 'openssl'
  # this has to be on top of this file, since the value can't be changed later
  OpenSSL::SSL::VERIFY_PEER = OpenSSL::SSL::VERIFY_NONE
end

module Spaceship
  # rubocop:disable Metrics/ClassLength
  class Client
    PROTOCOL_VERSION = "QH65B2"
    USER_AGENT = "Spaceship #{Fastlane::VERSION}"

    attr_reader :client

    # The user that is currently logged in
    attr_accessor :user

    # The logger in which all requests are logged
    # /tmp/spaceship[time]_[pid].log by default
    attr_accessor :logger

    attr_accessor :csrf_tokens

    # Base class for errors that want to present their message as
    # preferred error info for fastlane error handling. See:
    # fastlane_core/lib/fastlane_core/ui/fastlane_runner.rb
    class BasicPreferredInfoError < StandardError
      TITLE = 'The request could not be completed because:'.freeze

      def preferred_error_info
        message ? [TITLE, message] : nil
      end
    end

    # Invalid user credentials were provided
    class InvalidUserCredentialsError < BasicPreferredInfoError; end

    # Raised when no user credentials were passed at all
    class NoUserCredentialsError < BasicPreferredInfoError; end

    class ProgramLicenseAgreementUpdated < BasicPreferredInfoError
      def show_github_issues
        false
      end
    end

    # User doesn't have enough permission for given action
    class InsufficientPermissions < BasicPreferredInfoError
      TITLE = 'Insufficient permissions for your Apple ID:'.freeze

      def preferred_error_info
        message ? [TITLE, message] : nil
      end

      # We don't want to show similar GitHub issues, as the error message
      # should be pretty clear
      def show_github_issues
        false
      end
    end

    class UnexpectedResponse < StandardError
      attr_reader :error_info

      def initialize(error_info = nil)
        super(error_info)
        @error_info = error_info
      end

      def preferred_error_info
        return nil unless @error_info.kind_of?(Hash) && @error_info['resultString']

        [
          "Apple provided the following error info:",
          @error_info['resultString'],
          @error_info['userString']
        ].compact.uniq # sometimes 'resultString' and 'userString' are the same value
      end
    end

    # Raised when 302 is received from portal request
    class AppleTimeoutError < BasicPreferredInfoError; end

    # Raised when 401 is received from portal request
    class UnauthorizedAccessError < BasicPreferredInfoError; end

    # Raised when 500 is received from iTunes Connect
    class InternalServerError < BasicPreferredInfoError; end

    # Authenticates with Apple's web services. This method has to be called once
    # to generate a valid session. The session will automatically be used from then
    # on.
    #
    # This method will automatically use the username from the Appfile (if available)
    # and fetch the password from the Keychain (if available)
    #
    # @param user (String) (optional): The username (usually the email address)
    # @param password (String) (optional): The password
    #
    # @raise InvalidUserCredentialsError: raised if authentication failed
    #
    # @return (Spaceship::Client) The client the login method was called for
    def self.login(user = nil, password = nil)
      instance = self.new
      if instance.login(user, password)
        instance
      else
        raise InvalidUserCredentialsError.new, "Invalid User Credentials"
      end
    end

    def self.hostname
      raise "You must implement self.hostname"
    end

    # @return (Array) A list of all available teams
    def teams
      user_details_data['associatedAccounts'].sort_by do |team|
        [
          team['contentProvider']['name'],
          team['contentProvider']['contentProviderId']
        ]
      end
    end

    def user_details_data
      return @_cached_user_details if @_cached_user_details
      r = request(:get, '/WebObjects/iTunesConnect.woa/ra/user/detail')
      @_cached_user_details = parse_response(r, 'data')
    end

    # @return (String) The currently selected Team ID
    def team_id
      return @current_team_id if @current_team_id

      if teams.count > 1
        puts "The current user is in #{teams.count} teams. Pass a team ID or call `select_team` to choose a team. Using the first one for now."
      end
      @current_team_id ||= teams[0]['contentProvider']['contentProviderId']
    end

    # Set a new team ID which will be used from now on
    def team_id=(team_id)
      # First, we verify the team actually exists, because otherwise iTC would return the
      # following confusing error message
      #
      #     invalid content provider id
      #
      available_teams = teams.collect do |team|
        (team["contentProvider"] || {})["contentProviderId"]
      end

      result = available_teams.find do |available_team_id|
        team_id.to_s == available_team_id.to_s
      end

      unless result
        raise ITunesConnectError.new, "Could not set team ID to '#{team_id}', only found the following available teams: #{available_teams.join(', ')}"
      end

      response = request(:post) do |req|
        req.url "ra/v1/session/webSession"
        req.body = {
          contentProviderId: team_id,
          dsId: user_detail_data.ds_id # https://github.com/fastlane/fastlane/issues/6711
        }.to_json
        req.headers['Content-Type'] = 'application/json'
      end

      handle_itc_response(response.body)

      @current_team_id = team_id
    end

    # Instantiates a client but with a cookie derived from another client.
    #
    # HACK: since the `@cookie` is not exposed, we use this hacky way of sharing the instance.
    def self.client_with_authorization_from(another_client)
      self.new(cookie: another_client.instance_variable_get(:@cookie), current_team_id: another_client.team_id)
    end

    def initialize(cookie: nil, current_team_id: nil)
      options = {
       request: {
          timeout:       (ENV["SPACESHIP_TIMEOUT"] || 300).to_i,
          open_timeout:  (ENV["SPACESHIP_TIMEOUT"] || 300).to_i
        }
      }
      @current_team_id = current_team_id
      @cookie = cookie || HTTP::CookieJar.new
      @client = Faraday.new(self.class.hostname, options) do |c|
        c.response :json, content_type: /\bjson$/
        c.response :xml, content_type: /\bxml$/
        c.response :plist, content_type: /\bplist$/
        c.use :cookie_jar, jar: @cookie
        c.adapter Faraday.default_adapter

        if ENV['SPACESHIP_DEBUG']
          # for debugging only
          # This enables tracking of networking requests using Charles Web Proxy
          c.proxy "https://127.0.0.1:8888"
        end

        if ENV["DEBUG"]
          puts "To run _spaceship_ through a local proxy, use SPACESHIP_DEBUG"
        end
      end
    end

    # The logger in which all requests are logged
    # /tmp/spaceship[time]_[pid].log by default
    def logger
      unless @logger
        if ENV["VERBOSE"]
          @logger = Logger.new(STDOUT)
        else
          # Log to file by default
          path = "/tmp/spaceship#{Time.now.to_i}_#{Process.pid}.log"
          @logger = Logger.new(path)
        end

        @logger.formatter = proc do |severity, datetime, progname, msg|
          "[#{datetime.strftime('%H:%M:%S')}]: #{msg}\n"
        end
      end

      @logger
    end

    ##
    # Return the session cookie.
    #
    # @return (String) the cookie-string in the RFC6265 format: https://tools.ietf.org/html/rfc6265#section-4.2.1
    def cookie
      @cookie.map(&:to_s).join(';')
    end

    def store_cookie(path: nil)
      path ||= persistent_cookie_path
      FileUtils.mkdir_p(File.expand_path("..", path))

      # really important to specify the session to true
      # otherwise myacinfo and more won't be stored
      @cookie.save(path, :yaml, session: true)
      return File.read(path)
    end

    # This is a duplicate method of fastlane_core/fastlane_core.rb#fastlane_user_dir
    def fastlane_user_dir
      path = File.expand_path(File.join("~", ".fastlane"))
      FileUtils.mkdir_p(path) unless File.directory?(path)
      return path
    end

    # Returns preferred path for storing cookie
    # for two step verification.
    def persistent_cookie_path
      if ENV["SPACESHIP_COOKIE_PATH"]
        path = File.expand_path(File.join(ENV["SPACESHIP_COOKIE_PATH"], "spaceship", self.user, "cookie"))
      else
        [File.join(self.fastlane_user_dir, "spaceship"), "~/.spaceship", "/var/tmp/spaceship", "#{Dir.tmpdir}/spaceship"].each do |dir|
          dir_parts = File.split(dir)
          if directory_accessible?(File.expand_path(dir_parts.first))
            path = File.expand_path(File.join(dir, self.user, "cookie"))
            break
          end
        end
      end

      return path
    end

    #####################################################
    # @!group Automatic Paging
    #####################################################

    # The page size we want to request, defaults to 500
    def page_size
      @page_size ||= 500
    end

    # Handles the paging for you... for free
    # Just pass a block and use the parameter as page number
    def paging
      page = 0
      results = []
      loop do
        page += 1
        current = yield(page)

        results += current

        break if (current || []).count < page_size # no more results
      end

      return results
    end

    #####################################################
    # @!group Login and Team Selection
    #####################################################

    # Authenticates with Apple's web services. This method has to be called once
    # to generate a valid session. The session will automatically be used from then
    # on.
    #
    # This method will automatically use the username from the Appfile (if available)
    # and fetch the password from the Keychain (if available)
    #
    # @param user (String) (optional): The username (usually the email address)
    # @param password (String) (optional): The password
    #
    # @raise InvalidUserCredentialsError: raised if authentication failed
    #
    # @return (Spaceship::Client) The client the login method was called for
    def login(user = nil, password = nil)
      if user.to_s.empty? or password.to_s.empty?
        require 'credentials_manager'

        keychain_entry = CredentialsManager::AccountManager.new(user: user, password: password)
        user ||= keychain_entry.user
        password = keychain_entry.password
      end

      if user.to_s.strip.empty? or password.to_s.strip.empty?
        raise NoUserCredentialsError.new, "No login data provided"
      end

      self.user = user
      @password = password
      begin
        do_login(user, password)
      rescue InvalidUserCredentialsError => ex
        raise ex unless keychain_entry

        if keychain_entry.invalid_credentials
          login(user)
        else
          puts "Please run this tool again to apply the new password"
        end
      end
    end

    # This method is used for both the Apple Dev Portal and iTunes Connect
    # This will also handle 2 step verification
    def send_shared_login_request(user, password)
      # First we see if we have a stored cookie for 2 step enabled accounts
      # this is needed as it stores the information on if this computer is a
      # trusted one. In general I think spaceship clients should be trusted
      load_session_from_file
      # If this is a CI, the user can pass the session via environment variable
      load_session_from_env

      data = {
        accountName: user,
        password: password,
        rememberMe: true
      }

      begin
        # The below workaround is only needed for 2 step verified machines
        # Due to escaping of cookie values we have a little workaround here
        # By default the cookie jar would generate the following header
        #   DES5c148...=HSARM.......xaA/O69Ws/CHfQ==SRVT
        # However we need the following
        #   DES5c148...="HSARM.......xaA/O69Ws/CHfQ==SRVT"
        # There is no way to get the cookie jar value with " around the value
        # so we manually modify the cookie (only this one) to be properly escaped
        # Afterwards we pass this value manually as a header
        # It's not enough to just modify @cookie, it needs to be done after self.cookie
        # as a string operation
        important_cookie = @cookie.store.entries.find { |a| a.name.include?("DES") }
        if important_cookie
          modified_cookie = self.cookie # returns a string of all cookies
          unescaped_important_cookie = "#{important_cookie.name}=#{important_cookie.value}"
          escaped_important_cookie = "#{important_cookie.name}=\"#{important_cookie.value}\""
          modified_cookie.gsub!(unescaped_important_cookie, escaped_important_cookie)
        end

        response = request(:post) do |req|
          req.url "https://idmsa.apple.com/appleauth/auth/signin?widgetKey=#{itc_service_key}"
          req.body = data.to_json
          req.headers['Content-Type'] = 'application/json'
          req.headers['X-Requested-With'] = 'XMLHttpRequest'
          req.headers['Accept'] = 'application/json, text/javascript'
          req.headers["Cookie"] = modified_cookie if modified_cookie
        end
      rescue UnauthorizedAccessError
        raise InvalidUserCredentialsError.new, "Invalid username and password combination. Used '#{user}' as the username."
      end

      # get woinst, wois, and itctx cookie values
      request(:get, "https://itunesconnect.apple.com/WebObjects/iTunesConnect.woa/wa")

      case response.status
      when 403
        raise InvalidUserCredentialsError.new, "Invalid username and password combination. Used '#{user}' as the username."
      when 200
        return response
      else
        location = response["Location"]
        if location && URI.parse(location).path == "/auth" # redirect to 2 step auth page
          handle_two_step(response)
          return true
        elsif (response.body || "").include?('invalid="true"')
          # User Credentials are wrong
          raise InvalidUserCredentialsError.new, "Invalid username and password combination. Used '#{user}' as the username."
        elsif (response['Set-Cookie'] || "").include?("itctx")
          raise "Looks like your Apple ID is not enabled for iTunes Connect, make sure to be able to login online"
        else
          info = [response.body, response['Set-Cookie']]
          raise TunesClient::ITunesConnectError.new, info.join("\n")
        end
      end
    end

    def itc_service_key
      return @service_key if @service_key

      # Check if we have a local cache of the key
      itc_service_key_path = "/tmp/spaceship_itc_service_key.txt"
      return File.read(itc_service_key_path) if File.exist?(itc_service_key_path)

      # Some customers in Asia have had trouble with the CDNs there that cache and serve this content, leading
      # to "buffer error (Zlib::BufError)" from deep in the Ruby HTTP stack. Setting this header requests that
      # the content be served only as plain-text, which seems to work around their problem, while not affecting
      # other clients.
      #
      # https://github.com/fastlane/fastlane/issues/4610
      headers = { 'Accept-Encoding' => 'identity' }
      # We need a service key from a JS file to properly auth
      js = request(:get, "https://itunesconnect.apple.com/itc/static-resources/controllers/login_cntrl.js", nil, headers)
      @service_key = js.body.match(/itcServiceKey = '(.*)'/)[1]

      # Cache the key locally
      File.write(itc_service_key_path, @service_key)

      return @service_key
    rescue => ex
      puts ex.to_s
      raise AppleTimeoutError.new, "Could not receive latest API key from iTunes Connect, this might be a server issue."
    end

    #####################################################
    # @!group Helpers
    #####################################################

    def with_retry(tries = 5, &_block)
      return yield
    rescue Faraday::Error::ConnectionFailed, Faraday::Error::TimeoutError, AppleTimeoutError => ex # New Faraday version: Faraday::TimeoutError => ex
      tries -= 1
      unless tries.zero?
        logger.warn("Timeout received: '#{ex.message}'. Retrying after 3 seconds (remaining: #{tries})...")
        sleep 3 unless defined? SpecHelper
        retry
      end
      raise ex # re-raise the exception
    rescue UnauthorizedAccessError => ex
      if @loggedin && !(tries -= 1).zero?
        msg = "Auth error received: '#{ex.message}'. Login in again then retrying after 3 seconds (remaining: #{tries})..."
        puts msg if Spaceship::Globals.verbose?
        logger.warn msg

        if self.class.spaceship_session_env.to_s.length > 0
          raise UnauthorizedAccessError.new, "Authentication error, you passed an invalid session using the environment variable FASTLANE_SESSION or SPACESHIP_SESSION"
        end

        do_login(self.user, @password)
        sleep 3 unless defined? SpecHelper
        retry
      end
      raise ex # re-raise the exception
    end

    # memorize the last csrf tokens from responses
    def csrf_tokens
      @csrf_tokens || {}
    end

    def request(method, url_or_path = nil, params = nil, headers = {}, &block)
      headers.merge!(csrf_tokens)
      headers['User-Agent'] = USER_AGENT

      # Before encoding the parameters, log them
      log_request(method, url_or_path, params)

      # form-encode the params only if there are params, and the block is not supplied.
      # this is so that certain requests can be made using the block for more control
      if method == :post && params && !block_given?
        params, headers = encode_params(params, headers)
      end

      response = send_request(method, url_or_path, params, headers, &block)

      log_response(method, url_or_path, response)

      return response
    end

    def parse_response(response, expected_key = nil)
      if response.body
        # If we have an `expected_key`, select that from response.body Hash
        # Else, don't.

        # the returned error message and info, is html encoded ->  &quot;issued&quot; -> make this readable ->  "issued"
        response.body["userString"] = CGI.unescapeHTML(response.body["userString"]) if response.body["userString"]
        response.body["resultString"] = CGI.unescapeHTML(response.body["resultString"]) if response.body["resultString"]

        content = expected_key ? response.body[expected_key] : response.body
      end
      if content.nil?
        detect_most_common_errors_and_raise_exceptions(response.body) if response.body
        raise UnexpectedResponse, response.body
      elsif content.kind_of?(Hash) && (content["resultString"] || "").include?("NotAllowed")
        # example content when doing a Developer Portal action with not enough permission
        # => {"responseId"=>"e5013d83-c5cb-4ba0-bb62-734a8d56007f",
        #    "resultCode"=>1200,
        #    "resultString"=>"webservice.certificate.downloadNotAllowed",
        #    "userString"=>"You are not permitted to download this certificate.",
        #    "creationTimestamp"=>"2017-01-26T22:44:13Z",
        #    "protocolVersion"=>"QH65B2",
        #    "userLocale"=>"en_US",
        #    "requestUrl"=>"https://developer.apple.com/services-account/QH65B2/account/ios/certificate/downloadCertificateContent.action",
        #    "httpCode"=>200}
        raise_insuffient_permission_error!(additional_error_string: content["userString"])
      else
        store_csrf_tokens(response)
        content
      end
    end

    def detect_most_common_errors_and_raise_exceptions(body)
      # Check if the failure is due to missing permissions (iTunes Connect)
      if body["messages"] && body["messages"]["error"].include?("Forbidden")
        raise_insuffient_permission_error!
      elsif body.to_s.include?("Internal Server Error - Read")
        raise InternalServerError, "Received an internal server error from iTunes Connect / Developer Portal, please try again later"
      elsif (body["resultString"] || "").include?("Program License Agreement")
        raise ProgramLicenseAgreementUpdated, "#{body['userString']} Please manually log into iTunes Connect to review and accept the updated agreement."
      end
    end

    # This also gets called from subclasses
    def raise_insuffient_permission_error!(additional_error_string: nil)
      # get the method name of the request that failed
      # `block in` is used very often for requests when surrounded for paging or retrying blocks
      # The ! is part of some methods when they modify or delete a resource, so we don't want to show it
      # Using `sub` instead of `delete` as we don't want to allow multiple matches
      calling_method_name = caller_locations(2, 2).first.label.sub("block in", "").delete("!").strip
      begin
        team_id = "(Team ID #{self.team_id}) "
      rescue
        # Showing the team ID is something that's nice to have, however it might cause an exception
        # when the user doesn't have any permission at all (e.g. failing at login)
        # we still want the error message to show the actual string, but without the team_id in that case
        team_id = ""
      end
      error_message = "User #{self.user} #{team_id}doesn't have enough permission for the following action: #{calling_method_name}"
      error_message += " (#{additional_error_string})" if additional_error_string.to_s.length > 0
      raise InsufficientPermissions, error_message
    end

    private

    def directory_accessible?(path)
      Dir.exist?(File.expand_path(path))
    end

    def do_login(user, password)
      @loggedin = false
      ret = send_login_request(user, password) # different in subclasses
      @loggedin = true
      ret
    end

    # Is called from `parse_response` to store the latest csrf_token (if available)
    def store_csrf_tokens(response)
      if response and response.headers
        tokens = response.headers.select { |k, v| %w(csrf csrf_ts).include?(k) }
        if tokens and !tokens.empty?
          @csrf_tokens = tokens
        end
      end
    end

    def log_request(method, url, params)
      params_to_log = Hash(params).dup # to also work with nil
      params_to_log.delete(:accountPassword) # Dev Portal
      params_to_log.delete(:theAccountPW) # iTC
      params_to_log = params_to_log.collect do |key, value|
        "{#{key}: #{value}}"
      end
      logger.info(">> #{method.upcase}: #{url} #{params_to_log.join(', ')}")
    end

    def log_response(method, url, response)
      body = response.body.kind_of?(String) ? response.body.force_encoding(Encoding::UTF_8) : response.body
      logger.debug("<< #{method.upcase}: #{url}: #{body}")
    end

    # Actually sends the request to the remote server
    # Automatically retries the request up to 3 times if something goes wrong
    def send_request(method, url_or_path, params, headers, &block)
      with_retry do
        response = @client.send(method, url_or_path, params, headers, &block)
        resp_hash = response.to_hash
        if resp_hash[:status] == 401
          msg = "Auth lost"
          logger.warn msg
          raise UnauthorizedAccessError.new, "Unauthorized Access"
        end

        if response.body.to_s.include?("<title>302 Found</title>")
          raise AppleTimeoutError.new, "Apple 302 detected"
        end
        return response
      end
    end

    def encode_params(params, headers)
      params = Faraday::Utils::ParamsHash[params].to_query
      headers = { 'Content-Type' => 'application/x-www-form-urlencoded' }.merge(headers)
      return params, headers
    end
  end
  # rubocop:enable Metrics/ClassLength
end

require 'spaceship/two_step_client'
