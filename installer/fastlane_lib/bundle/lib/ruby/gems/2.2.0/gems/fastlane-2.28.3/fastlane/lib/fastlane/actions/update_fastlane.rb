require 'rubygems/spec_fetcher'
require 'rubygems/command_manager'

module Fastlane
  module Actions
    # Makes sure fastlane tools are up-to-date when running fastlane
    class UpdateFastlaneAction < Action
      ALL_TOOLS = ["fastlane"]

      def self.run(options)
        if options[:no_update]
          return
        end

        tools_to_update = options[:tools].split ',' unless options[:tools].nil?
        tools_to_update ||= all_installed_tools

        if tools_to_update.count == 0
          UI.error("No tools specified or couldn't find any installed fastlane.tools")
          return
        end

        UI.message("Looking for updates for #{tools_to_update.join(', ')}...")

        updater = Gem::CommandManager.instance[:update]
        cleaner = Gem::CommandManager.instance[:cleanup]

        sudo_needed = !File.writable?(Gem.dir)

        if sudo_needed
          UI.important("It seems that your Gem directory is not writable by your current User.")
          UI.important("Fastlane would need sudo rights to update itself, however, running 'sudo fastlane' is not recommended.")
          UI.important("If you still want to use this action, please read the Actions.md documentation on a guide how to set this up.")
          return
        end

        unless updater.respond_to?(:highest_installed_gems)
          UI.important "The update_fastlane action requires rubygems version 2.1.0 or greater."
          UI.important "Please update your version of ruby gems before proceeding."
          UI.command "gem install rubygems-update"
          UI.command "update_rubygems"
          UI.command "gem update --system"
          return
        end

        highest_versions = updater.highest_installed_gems.keep_if { |key| tools_to_update.include? key }
        update_needed = updater.which_to_update(highest_versions, tools_to_update)

        if update_needed.count == 0
          UI.success("Nothing to update ✅")
          return
        end

        # suppress updater output - very noisy
        Gem::DefaultUserInteraction.ui = Gem::SilentUI.new

        update_needed.each do |tool_info|
          tool = tool_info[0]
          local_version = Gem::Version.new(highest_versions[tool].version)
          update_url = FastlaneCore::UpdateChecker.generate_fetch_url(tool)
          latest_version = FastlaneCore::UpdateChecker.fetch_latest(update_url)
          UI.message("Updating #{tool} from #{local_version} to #{latest_version} ... 🚀")

          # Approximate_recommendation will create a string like "~> 0.10" from a version 0.10.0, e.g. one that is valid for versions >= 0.10 and <1.0
          updater.update_gem tool, Gem::Requirement.new(local_version.approximate_recommendation)

          UI.message("Finished updating #{tool}")
        end

        all_updated_tools = updater.installer.installed_gems.select do |updated_tool|
          updated_tool.version > highest_versions[updated_tool.name].version if highest_versions[updated_tool.name]
        end

        if all_updated_tools.empty?
          UI.message("All fastlane tools are up-to-date!")
        else
          UI.message("Cleaning up old versions...")
          cleaner.options[:args] = all_updated_tools.map(&:name)
          cleaner.execute
          UI.message("fastlane.tools successfully updated! I will now restart myself... 😴")

          # Set no_update to true so we don't try to update again
          exec "FL_NO_UPDATE=true #{$PROGRAM_NAME} #{ARGV.join ' '}"
        end
      end

      def self.all_installed_tools
        Gem::Specification.select { |s| ALL_TOOLS.include? s.name }.map(&:name).uniq
      end

      def self.description
        "Makes sure fastlane-tools are up-to-date when running fastlane"
      end

      def self.details
        [
          "This action will look at all installed fastlane tools and update them to the next available minor version - major version updates will not be performed automatically, as they might include breaking changes. If an update was performed, fastlane will be restarted before the run continues.",
          "If you are using rbenv or rvm, everything should be good to go. However, if you are using the system's default ruby, some additional setup is needed for this action to work correctly. In short, fastlane needs to be able to access your gem library without running in `sudo` mode.",
          "The simplest possible fix for this is putting the following lines into your `~/.bashrc` or `~/.zshrc` file:",
          "```bash",
          "export GEM_HOME=~/.gems",
          "export PATH=$PATH:~/.gems/bin",
          "```",
          "After the above changes, restart your terminal, then run `mkdir $GEM_HOME` to create the new gem directory. After this, you're good to go!",
          "Recommended usage of the `update_fastlane` action is at the top of the `before_all` block, before running any other action"
        ].join("\n\n")
      end

      def self.available_options
        [
          FastlaneCore::ConfigItem.new(key: :tools,
                                       env_name: "FL_TOOLS_TO_UPDATE",
                                       description: "Comma separated list of fastlane tools to update (e.g. fastlane,deliver,sigh). If not specified, all currently installed fastlane-tools will be updated",
                                       optional: true),
          FastlaneCore::ConfigItem.new(key: :no_update,
                                       env_name: "FL_NO_UPDATE",
                                       description: "Don't update during this run. Defaults to false",
                                       is_string: false,
                                       default_value: false)
        ]
      end

      def self.author
        "milch"
      end

      def self.is_supported?(platform)
        true
      end

      def self.example_code
        [
          'before_all do
            update_fastlane
            # ...
          end'
        ]
      end

      def self.category
        :misc
      end
    end
  end
end
