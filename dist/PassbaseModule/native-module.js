var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { NativeModules } from 'react-native';
const { RNPassbaseModule } = NativeModules;
const init = (publishableApiKey, onSuccess, onFailure) => __awaiter(this, void 0, void 0, function* () {
    const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
        (onFailure && typeof onFailure === 'function');
    try {
        if (!publishableApiKey) {
            throw new Error(RNPassbaseModule.REQUIRED_OPTION_API_KEY_MISSING);
        }
        if (isCallbackBased) {
            return RNPassbaseModule.initWithCB(publishableApiKey, onSuccess, onFailure);
        }
        else {
            return RNPassbaseModule.initialize(publishableApiKey);
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
const setPrefillUserEmail = (email) => RNPassbaseModule.setPrefillUserEmail(email);
const startVerification = (onSuccess, onFailure) => __awaiter(this, void 0, void 0, function* () {
    // todo: make sure to chekc internet connection as verificaiton doesn't start without internet.
    const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
        (onFailure && typeof onFailure === 'function');
    if (isCallbackBased) {
        return RNPassbaseModule.startVerificationWithCB(onSuccess, onFailure);
    }
    return RNPassbaseModule.startVerification();
});
const show = (message) => RNPassbaseModule.show(message);
export const NativeModule = Object.assign({}, RNPassbaseModule, { init,
    startVerification,
    show,
    setPrefillUserEmail, constants: {
        ERROR_INITIALIZING_PASSBASE: RNPassbaseModule.ERROR_INITIALIZING_PASSBASE,
        INITIALZE_PASSBASE_TO_START_VERIFICATION: RNPassbaseModule.INITIALZE_PASSBASE_TO_START_VERIFICATION,
        ERROR_START_VERIFICATION: RNPassbaseModule.ERROR_START_VERIFICATION,
        VERIFICATION_CANCELLED: RNPassbaseModule.VERIFICATION_CANCELLED,
        REQUIRED_OPTION_API_KEY_MISSING: RNPassbaseModule.REQUIRED_OPTION_API_KEY_MISSING,
        SUCCESS: RNPassbaseModule.SUCCESS,
        ERROR: RNPassbaseModule.ERROR
    } });
//# sourceMappingURL=native-module.js.map