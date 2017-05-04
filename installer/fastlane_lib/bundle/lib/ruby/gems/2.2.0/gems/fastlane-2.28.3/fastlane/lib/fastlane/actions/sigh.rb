module Fastlane
  module Actions
    module SharedValues
      SIGH_PROFILE_PATH = :SIGH_PROFILE_PATH
      SIGH_PROFILE_PATHS = :SIGH_PROFILE_PATHS
      SIGH_UDID = :SIGH_UDID # deprecated
      SIGH_UUID = :SIGH_UUID
      SIGH_PROFILE_TYPE = :SIGH_PROFILE_TYPE
    end

    class SighAction < Action
      def self.run(values)
        require 'sigh'
        require 'credentials_manager/appfile_config'

        Sigh.config = values # we already have the finished config

        path = Sigh::Manager.start

        Actions.lane_context[SharedValues::SIGH_PROFILE_PATH] = path # absolute path
        Actions.lane_context[SharedValues::SIGH_PROFILE_PATHS] ||= []
        Actions.lane_context[SharedValues::SIGH_PROFILE_PATHS] << path

        uuid = ENV["SIGH_UUID"] || ENV["SIGH_UDID"] # the UUID of the profile
        Actions.lane_context[SharedValues::SIGH_UUID] = Actions.lane_context[SharedValues::SIGH_UDID] = uuid if uuid

        set_profile_type(values, ENV["SIGH_PROFILE_ENTERPRISE"])

        return uuid # returs uuid of profile
      end

      def self.set_profile_type(values, enterprise)
        profile_type = "app-store"
        profile_type = "ad-hoc" if values[:adhoc]
        profile_type = "development" if values[:development]
        profile_type = "enterprise" if enterprise

        UI.message("Setting Provisioning Profile type to '#{profile_type}'")

        Actions.lane_context[SharedValues::SIGH_PROFILE_TYPE] = profile_type
      end

      def self.description
        "Generates a provisioning profile. Stores the profile in the current folder"
      end

      def self.author
        "KrauseFx"
      end

      def self.return_value
        "The UUID of the profile sigh just fetched/generated"
      end

      def self.details
        "**Note**: It is recommended to use [match](https://github.com/fastlane/fastlane/tree/master/match) according to the [codesigning.guide](https://codesigning.guide) for generating and maintaining your provisioning profiles. Use _sigh_ directly only if you want full control over what's going on and know more about codesigning."
      end

      def self.available_options
        require 'sigh'
        Sigh::Options.available_options
      end

      def self.is_supported?(platform)
        platform == :ios
      end

      def self.example_code
        [
          'sigh',
          'sigh(
            adhoc: true,
            force: true,
            filename: "myFile.mobileprovision"
          )'
        ]
      end

      def self.category
        :code_signing
      end
    end
  end
end
