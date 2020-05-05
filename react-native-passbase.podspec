require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name             = package['podname']
  s.version          = package['version']
  s.summary          = package['description']
  s.homepage         = package['homepage']
  s.license          = package['license']
  s.author           = package['author']
  s.platform         = :ios, '11.0'
  s.source           = { :git => "https://github.com/passbase/rn-passbase.git", :tag => "v#{s.version}" }
  s.source_files     = 'ios/**/*.{h,m,swift}'
  s.requires_arc     = true
  s.swift_version    = '5.0'

  s.dependency 'React'
  s.dependency 'Passbase', '1.7.15'
end

