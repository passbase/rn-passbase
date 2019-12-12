require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name             = package['name']
  s.version          = package['version']
  s.summary          = package['description']
  s.homepage         = package['homepage']
  s.license          = package['license']
  s.author           = package['author']
  s.platform         = :ios, '11.0'
  s.source           = { :git => 'file://' + __dir__ }
  s.source_files     = 'ios/**/*.{h,m,swift}'
  s.requires_arc     = true
  s.swift_version    = '5.0'

  s.dependency 'React'
  s.dependency 'Passbase', '~> 1.7.0'
end

