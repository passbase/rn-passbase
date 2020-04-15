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
const init = (apiKey, email, additionalAttribs, onSuccess, onFailure) => __awaiter(this, void 0, void 0, function* () {
    const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
        (onFailure && typeof onFailure === 'function');
    try {
        if (!apiKey) {
            throw new Error(RNPassbaseModule.REQUIRED_OPTION_API_KEY_MISSING);
        }
        // this check is here because SDKs Kotlin side accepts only Array<Pair<String, String>>
        if (additionalAttribs && Object.keys(additionalAttribs).length) {
            const keys = Object.keys(additionalAttribs);
            for (const key of keys) {
                // @ts-ignore
                let item = additionalAttribs[key];
                if (typeof item !== 'string') {
                    throw new Error('additional attributes should have only string type values.');
                }
            }
        }
        if (isCallbackBased) {
            return RNPassbaseModule.initWithCB(apiKey, email, additionalAttribs, onSuccess, onFailure);
        }
        else {
            return RNPassbaseModule.initialize(apiKey, email, additionalAttribs);
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
    show, constants: {
        ERROR_INITIALIZING_PASSBASE: RNPassbaseModule.ERROR_INITIALIZING_PASSBASE,
        INITIALZE_PASSBASE_TO_START_VERIFICATION: RNPassbaseModule.INITIALZE_PASSBASE_TO_START_VERIFICATION,
        ERROR_START_VERIFICATION: RNPassbaseModule.ERROR_START_VERIFICATION,
        VERIFICATION_CANCELLED: RNPassbaseModule.VERIFICATION_CANCELLED,
        REQUIRED_OPTION_API_KEY_MISSING: RNPassbaseModule.REQUIRED_OPTION_API_KEY_MISSING,
        SUCCESS: RNPassbaseModule.SUCCESS,
        ERROR: RNPassbaseModule.ERROR
    } });
//# sourceMappingURL=native-module.js.map