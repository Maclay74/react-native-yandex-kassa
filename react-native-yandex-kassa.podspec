require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-yandex-kassa"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-yandex-kassa
                   DESC
  s.homepage     = "https://github.com/github_account/react-native-yandex-kassa"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Your Name" => "yourname@email.com" }
  s.platforms    = { :ios => "9.0", :tvos => "10.0" }
  s.source       = { :git => "https://github.com/github_account/react-native-yandex-kassa.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true
<<<<<<< HEAD
  s.xcconfig = {'ENABLE_BITCODE' => 'NO'}

  s.dependency "React"
  s.dependency "YandexCheckoutPayments"
=======

  s.dependency "React"
	
  # s.dependency "..."
>>>>>>> 5e55e47eb1dbe2800591074a58f796af92baf284
end

