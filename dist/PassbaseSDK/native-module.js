var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { NativeModules } from 'react-native';
const { RNPassbaseSDK } = NativeModules;
const initialize = (publishableApiKey, customerPayload, onSuccess, onFailure) => __awaiter(this, void 0, void 0, function* () {
    const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
        (onFailure && typeof onFailure === 'function');
    try {
        if (!publishableApiKey) {
            throw new Error(RNPassbaseSDK.REQUIRED_OPTION_API_KEY_MISSING);
        }
        if (!customerPayload) {
            throw new Error(RNPassbaseSDK.REQUIRED_OPTION_API_KEY_MISSING);
        }
        if (isCallbackBased) {
            return RNPassbaseSDK.initWithCB(publishableApiKey, customerPayload, onSuccess, onFailure);
        }
        else {
            return RNPassbaseSDK.initialize(publishableApiKey, customerPayload);
        }
    }
    catch (ex) {
        if (isCallbackBased) {
            onFailure(ex);
        }
        else {
            return Promise.reject(ex);
        }
    }
});
const setPrefillUserEmail = (email) => RNPassbaseSDK.setPrefillUserEmail(email);
const startVerification = (onSuccess, onFailure) => __awaiter(this, void 0, void 0, function* () {
    // todo: make sure to chekc internet connection as verificaiton doesn't start without internet.
    const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
        (onFailure && typeof onFailure === 'function');
    if (isCallbackBased) {
        return RNPassbaseSDK.startVerificationWithCB(onSuccess, onFailure);
    }
    return RNPassbaseSDK.startVerification();
});
const show = (message) => RNPassbaseSDK.show(message);
export const NativeModule = Object.assign({}, RNPassbaseSDK, { initialize,
    startVerification,
    show,
    setPrefillUserEmail, constants: {
        ERROR_INITIALIZING_PASSBASE: RNPassbaseSDK.ERROR_INITIALIZING_PASSBASE,
        INITIALZE_PASSBASE_TO_START_VERIFICATION: RNPassbaseSDK.INITIALZE_PASSBASE_TO_START_VERIFICATION,
        ERROR_START_VERIFICATION: RNPassbaseSDK.ERROR_START_VERIFICATION,
        VERIFICATION_CANCELLED: RNPassbaseSDK.VERIFICATION_CANCELLED,
        REQUIRED_OPTION_API_KEY_MISSING: RNPassbaseSDK.REQUIRED_OPTION_API_KEY_MISSING,
        REQUIRED_OPTION_CUSTOMER_PAYLOAD_MISSING: RNPassbaseSDK.REQUIRED_OPTION_CUSTOMER_PAYLOAD_MISSING,
        SUCCESS: RNPassbaseSDK.SUCCESS,
        ERROR: RNPassbaseSDK.ERROR
    } });
//# sourceMappingURL=native-module.js.map