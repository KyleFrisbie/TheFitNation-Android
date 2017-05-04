module Fastlane
  module Actions
    class SupplyAction < Action
      def self.run(params)
        require 'supply'
        require 'supply/options'

        # If no APK params were provided, try to fill in the values from lane context, preferring
        # the multiple APKs over the single APK if set.
        if params[:apk_paths].nil? && params[:apk].nil?
          all_apk_paths = Actions.lane_context[SharedValues::GRADLE_ALL_APK_OUTPUT_PATHS] || []
          if all_apk_paths.size > 1
            params[:apk_paths] = all_apk_paths
          else
            params[:apk] = Actions.lane_context[SharedValues::GRADLE_APK_OUTPUT_PATH]
          end
        end

        Supply.config = params # we already have the finished config

        Supply::Uploader.new.perform_upload
      end

      #####################################################
      # @!group Documentation
      #####################################################

      def self.description
        "Upload metadata, screenshots and binaries to Google Play"
      end

      def self.details
        "More information: https://github.com/fastlane/fastlane/tree/master/supply"
      end

      def self.available_options
        require 'supply'
        require 'supply/options'
        Supply::Options.available_options
      end

      def self.output
      end

      def self.return_value
      end

      def self.authors
        ["KrauseFx"]
      end

      def self.is_supported?(platform)
        platform == :android
      end

      def self.example_code
        [
          'supply'
        ]
      end

      def self.category
        :production
      end
    end
  end
end
