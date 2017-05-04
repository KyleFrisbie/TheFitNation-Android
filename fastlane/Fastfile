# More documentation about how to customize your build
# can be found here:
# https://docs.fastlane.tools
fastlane_version "1.109.0"

# This value helps us track success metrics for Fastfiles
# we automatically generate. Feel free to remove this line
# once you get things running smoothly!
generated_fastfile_id "0fa42704-fd54-419b-b512-4029826fd90b"

default_platform :android

# Fastfile actions accept additional configuration, but
# don't worry, fastlane will prompt you for required
# info which you can add here later
lane :beta do
  # build the release variant
  gradle(task: "assembleDebug")

  # upload to Beta by Crashlytics
  crashlytics(
    api_token: "82977f91de386a5526b052cdb918975d2ebbb85b",
    build_secret: "9236b25b3d7ef612f7c8e5fc4911a95b74490e4eff0b2359c32697d349c07d6c"
  )
end
