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

    /*
    * Method to enable/disable setTestMode
    * */
    @objc func setTestMode(_ enabled: Bool) {
        // ios sdk yet don't have this method exposed.
    }

    func didCompletePassbaseVerification (authenticationKey: String) {
        super.sendEvent(withName: "onCompletePassbaseVerification", body: ["authKey": authenticationKey])

        // todo: it is for back compatibility at the moment but will remove in futre releases.
        super.sendEvent(withName: "onCompletePassbase", body: ["authKey": authenticationKey])
    }

    func didCancelPassbaseVerification () {
        super.sendEvent(withName: "onCancelPassbaseVerification", body: nil)

        // todo: it is for back compatibility at the moment but will remove in futre releases.
        super.sendEvent(withName: "onCancelPassbase", body: nil)
    }

    func didStartPassbaseVerification() {
        super.sendEvent(withName: "onStartPassbaseVerification", body: nil)
    }
}
