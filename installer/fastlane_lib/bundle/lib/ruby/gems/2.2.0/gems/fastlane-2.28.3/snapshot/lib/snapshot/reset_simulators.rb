module Snapshot
  class ResetSimulators
    def self.clear_everything!(ios_versions, force = false)
      # !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      # !! Warning: This script will remove all your existing simulators !!
      # !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

      sure = true if FastlaneCore::Env.truthy?("SNAPSHOT_FORCE_DELETE") || force
      sure = UI.confirm("Are you sure? All your simulators will be DELETED and new ones will be created!") unless sure
      UI.user_error!("User cancelled action") unless sure

      devices.each do |device|
        _, name, id = device
        puts "Removing device #{name} (#{id})"
        `xcrun simctl delete #{id}`
      end

      all_runtime_type = `xcrun simctl list runtimes`.scan(/(.*)\s\(.*\((.*)\)/)
      # == Runtimes ==
      # iOS 9.3 (9.3 - 13E233) (com.apple.CoreSimulator.SimRuntime.iOS-9-3)
      # iOS 10.0 (10.0 - 14A345) (com.apple.CoreSimulator.SimRuntime.iOS-10-0)
      # iOS 10.1 (10.1 - 14B72) (com.apple.CoreSimulator.SimRuntime.iOS-10-1)
      # iOS 10.2 (10.2 - 14C89) (com.apple.CoreSimulator.SimRuntime.iOS-10-2)
      # tvOS 10.1 (10.1 - 14U591) (com.apple.CoreSimulator.SimRuntime.tvOS-10-1)
      # watchOS 3.1 (3.1 - 14S471a) (com.apple.CoreSimulator.SimRuntime.watchOS-3-1)
      ios_versions_ids = filter_runtimes(all_runtime_type, 'iOS', ios_versions)
      tv_version_ids = filter_runtimes(all_runtime_type, 'tvOS')
      watch_versions_ids = filter_runtimes(all_runtime_type, 'watchOS')

      all_device_types = `xcrun simctl list devicetypes`.scan(/(.*)\s\((.*)\)/)
      # == Device Types ==
      # iPhone 4s (com.apple.CoreSimulator.SimDeviceType.iPhone-4s)
      # iPhone 5 (com.apple.CoreSimulator.SimDeviceType.iPhone-5)
      # iPhone 5s (com.apple.CoreSimulator.SimDeviceType.iPhone-5s)
      # iPhone 6 (com.apple.CoreSimulator.SimDeviceType.iPhone-6)
      all_device_types.each do |device_type|
        if device_type.join(' ').include?("Watch")
          create(device_type, watch_versions_ids, 'watchOS')
        elsif device_type.join(' ').include?("TV")
          create(device_type, tv_version_ids, 'tvOS')
        else
          create(device_type, ios_versions_ids)
        end
      end

      make_phone_watch_pair
    end

    def self.create(device_type, os_versions, os_name = 'iOS')
      os_versions.each do |os_version|
        puts "Creating #{device_type[0]} for #{os_name} version #{os_version[0]}"
        `xcrun simctl create '#{device_type[0]}' #{device_type[1]} #{os_version[1]}`
      end
    end

    def self.filter_runtimes(all_runtimes, os = 'iOS', versions = [])
      all_runtimes.select { |v, id| v[/^#{os}/] }.select { |v, id| v[/#{versions.join("|")}$/] }
    end

    def self.devices
      all_devices = Helper.backticks('xcrun simctl list devices', print: FastlaneCore::Globals.verbose?)
      # == Devices ==
      # -- iOS 9.0 --
      #   iPhone 4s (32246EBC-33B0-47F9-B7BB-5C23C550DF29) (Shutdown)
      #   iPhone 5 (4B56C101-6B95-43D1-9485-3FBA0E127FFA) (Shutdown)
      #   iPhone 5s (6379C204-E82A-4FBD-8A22-6A01C7791D62) (Shutdown)
      # -- Unavailable: com.apple.CoreSimulator.SimRuntime.iOS-8-4 --
      #   iPhone 4s (FE9D6F85-1C51-4FE6-8597-FCAB5286B869) (Shutdown) (unavailable, runtime profile not found)

      result = all_devices.lines.map do |line|
        (line.match(/\s+(.+?)\s\(([\w\-]+)\).*/) || []).to_a
      end

      result.select { |parsed| parsed.length == 3 } # we don't care about those headers
    end

    def self.make_phone_watch_pair
      phones = []
      watches = []
      devices.each do |device|
        full_line, name, id = device
        phones << id if name.start_with?('iPhone 6') && device_line_usable?(full_line)
        watches << id if name.end_with?('mm') && device_line_usable?(full_line)
      end

      if phones.any? && watches.any?
        puts "Creating device pair of #{phones.last} and #{watches.last}"
        Helper.backticks("xcrun simctl pair #{watches.last} #{phones.last}", print: FastlaneCore::Globals.verbose?)
      end
    end

    def self.device_line_usable?(line)
      !line.include?("unavailable")
    end
  end
end
