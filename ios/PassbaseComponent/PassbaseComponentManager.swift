import Foundation
import Passbase

@objc (RNPassbaseComponentManager)
class PassbaseComponentManager: RCTViewManager {
    override func view() -> PassbaseButton! {
      return PassbaseButton(frame: CGRect(x: 40, y: 90, width: 300, height: 60))
    }
}
