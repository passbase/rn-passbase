import Foundation
import Passbase

@objc (RNPassbaseModule)
class PassbaseModule: RCTEventEmitter, PassbaseDelegate {
      @objc func show(_ message: String) {
        print(message);
      }

    @objc func initialize(_ apiKey: String, email: String, additionalParams: NSDictionary, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            if (!apiKey.isEmpty) {
                Passbase.source = 2

                Passbase.initialize(publishableApiKey: apiKey)
                Passbase.delegate = self
                Passbase.additionalAttributes = additionalParams as! [String : String]
                Passbase.prefillUserEmail = email
                var response = [String:Bool]()
                response["success"] = true
                resolve(response)
            } else {
                let error: NSError = NSError()
                reject("error_initializing_passbase_sdk", "required_option_api_key_is_missing", error)
            }
        } catch {
            let error: NSError = NSError()
            reject("error_initializing_passbase_sdk", "error_initializing_passbase_sdk", error)
        }
    }

    @objc func initWithCB(_ apiKey: String, email: String, additionalParams: NSDictionary, onSuccess: RCTResponseSenderBlock, onFailure: RCTResponseSenderBlock) {
        do {
            if (!apiKey.isEmpty) {
                Passbase.source = 2

                Passbase.initialize(publishableApiKey: apiKey)
                Passbase.delegate = self
                Passbase.additionalAttributes = additionalParams as! [String : String]
                Passbase.prefillUserEmail = email
                var response = [String:Bool]()
                response["success"] = true
                onSuccess([response])
            } else {
                onFailure([[
                    "error_initializing_passbase_sdk":"error_initializing_passbase_sdk",
                    "message": "required_option_api_key_is_missing"
                ]])
            }
        } catch {
                onFailure([[
                    "error_initializing_passbase_sdk":"error_initializing_passbase_sdk",
                    "message": "error_initializing_passbase_sdk"
                ]])
        }
    }

    @objc func startVerification(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
                try Passbase.startVerification(from: RCTPresentedViewController()!)
            resolve(["success": true])
        } catch {
            reject("error_starting_verification", "error_starting_verification", NSError())
        }
    }

    @objc func startVerificationWithCB(_ onSuccess: RCTResponseSenderBlock, onFailure: RCTResponseSenderBlock) {
        do {
                try Passbase.startVerification(from: RCTPresentedViewController()!)
            onSuccess([["success": true]])
        } catch {
            onFailure([["error_starting_verification":"error_starting_verification"]])
        }
    }

    @objc func setButtonBgColor (_ buttonBgColor: String) {
        if (!buttonBgColor.isEmpty) {
            Passbase.buttonUI.buttonBgColor = UIColor.init(buttonBgColor)
        }
    }

    @objc func setLoadingIndicatorColor (_ loadingIndicatorColor: String) {
        if (!loadingIndicatorColor.isEmpty) {
            Passbase.buttonUI.loadingIndicatorColor = UIColor.init(loadingIndicatorColor)
        }
    }

    @objc func setActionButtonBgColor (_ actionButtonBgColor: String) {
        if (!actionButtonBgColor.isEmpty) {
            Passbase.buttonUI.actionButtonBgColor = UIColor.init(actionButtonBgColor)
        }
    }

    @objc func setActionButtonDeactivatedBgColor (_ actionButtonDeactivatedBgColor: String) {
        if (!actionButtonDeactivatedBgColor.isEmpty) {
            Passbase.buttonUI.actionButtonDeactivatedBgColor = UIColor.init(actionButtonDeactivatedBgColor)
        }
    }

    @objc func setActionButtonTextColor (_ actionButtonTextColor: String) {
        if (!actionButtonTextColor.isEmpty) {
            Passbase.buttonUI.actionButtonTextColor = UIColor.init(actionButtonTextColor)
        }
    }

    @objc func setDisclaimerTextColor (_ disclaimerTextColor: String) {
        if (!disclaimerTextColor.isEmpty) {
            Passbase.buttonUI.disclaimerTextColor = UIColor.init(disclaimerTextColor)
        }
    }

    @objc func setTitleTextColor (_ titleTextColor: String) {
        if (!titleTextColor.isEmpty) {
            Passbase.buttonUI.titleTextColor = UIColor.init(titleTextColor)
        }
    }

    @objc func setSubtitleTextColor (_ subtitleTextColor: String) {
        if (!subtitleTextColor.isEmpty) {
            Passbase.buttonUI.subtitleTextColor = UIColor.init(subtitleTextColor)
        }
    }

    func didCompletePassbaseVerification (authenticationKey: String) {
        super.sendEvent(withName: "onCompletePassbase", body: ["authKey": authenticationKey])
    }

    func didCancelPassbaseVerification () {
        super.sendEvent(withName: "onCancelPassbase", body: nil)
    }
}
