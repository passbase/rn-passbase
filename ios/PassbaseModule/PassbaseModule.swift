import Foundation
import Passbase

@objc (RNPassbaseSDK)
class RNPassbaseSDK: RCTEventEmitter, PassbaseDelegate {
    @objc func show(_ message: String) {
        print(message);
    }

    @objc func setPrefillUserEmail(_ email: String) {
        PassbaseSDK.prefillUserEmail = email;
    }

    @objc func setMetaData(_ metaData: String) {
        PassbaseSDK.metaData = metaData;
    }

    @objc func setPrefillCountry(_ country: String) {
        PassbaseSDK.prefillCountry = country;
    }

    @objc func initialize(_ publishableApiKey: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        if (!publishableApiKey.isEmpty) {
            PassbaseSDK.source = 2
            PassbaseSDK.initialize(publishableApiKey: publishableApiKey)
            PassbaseSDK.delegate = self
            var response = [String:Bool]()
            response["success"] = true
            resolve(response)
        } else {
            let error: NSError = NSError()
            reject("error_initializing_passbase_sdk", "required_option_api_key_is_missing", error)
        }
    }

    @objc func initWithCB(_ publishableApiKey: String, onSuccess: RCTResponseSenderBlock, onFailure: RCTResponseSenderBlock) {
        if (!publishableApiKey.isEmpty) {
            PassbaseSDK.source = 2
            PassbaseSDK.initialize(publishableApiKey: publishableApiKey)
            PassbaseSDK.delegate = self
            var response = [String:Bool]()
            response["success"] = true
            onSuccess([response])
        } else {
            onFailure([[
                "error_initializing_passbase_sdk":"error_initializing_passbase_sdk",
                "message": "required_option_api_key_is_missing"
            ]])
        }
    }

    @objc func startVerification(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            try PassbaseSDK.startVerification(from: RCTPresentedViewController()!)
            resolve(["success": true])
        } catch {
            reject("error_starting_verification", "error_starting_verification", NSError())
        }
    }

    @objc func startVerificationWithCB(_ onSuccess: RCTResponseSenderBlock, onFailure: RCTResponseSenderBlock) {
        do {
            try PassbaseSDK.startVerification(from: RCTPresentedViewController()!)
            onSuccess([["success": true]])
        } catch {
            onFailure([["error_starting_verification":"error_starting_verification"]])
        }
    }


    func onFinish (identityAccessKey: String) {
        super.sendEvent(withName: "onFinish", body: ["identityAccessKey": identityAccessKey])
    }

    func onSubmitted (identityAccessKey: String) {
        super.sendEvent(withName: "onSubmitted", body: ["identityAccessKey": identityAccessKey])
    }

    func onError (errorCode: String) {
        super.sendEvent(withName: "onError", body: ["errorCode": errorCode])
    }

    func onStart() {
        super.sendEvent(withName: "onStart", body: nil)
    }
}
