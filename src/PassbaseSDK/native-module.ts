import { NativeModules } from 'react-native'
const { RNPassbaseSDK } = NativeModules

const initialize = async (publishableApiKey: string, onSuccess: Function, onFailure: Function) => {
  const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
    (onFailure && typeof onFailure === 'function')

  try {

    if (!publishableApiKey) {
      throw new Error(RNPassbaseSDK.REQUIRED_OPTION_API_KEY_MISSING)
    }

    if (isCallbackBased) {
      return RNPassbaseSDK.initWithCB(publishableApiKey, onSuccess, onFailure)
    } else {
      return RNPassbaseSDK.initialize(publishableApiKey)
    }

  } catch (ex) {
    if (isCallbackBased) {
      onFailure(ex)

    } else {
      return Promise.reject(ex)

    }
  }
}

const setPrefillUserEmail = (email: string) => RNPassbaseSDK.setPrefillUserEmail(email);

const startVerification = async (onSuccess: Function, onFailure: Function) => {
  // todo: make sure to chekc internet connection as verificaiton doesn't start without internet.
  const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
    (onFailure && typeof onFailure === 'function')

  if (isCallbackBased) {
    return RNPassbaseSDK.startVerificationWithCB(onSuccess, onFailure)
  }
  return RNPassbaseSDK.startVerification()
}
const show = (message: string) =>  RNPassbaseSDK.show(message);

export const NativeModule = {
  ...RNPassbaseSDK,
  initialize,
  startVerification,
  show,
  setPrefillUserEmail,
  constants: {
    ERROR_INITIALIZING_PASSBASE: RNPassbaseSDK.ERROR_INITIALIZING_PASSBASE,
    INITIALZE_PASSBASE_TO_START_VERIFICATION: RNPassbaseSDK.INITIALZE_PASSBASE_TO_START_VERIFICATION,
    ERROR_START_VERIFICATION: RNPassbaseSDK.ERROR_START_VERIFICATION,
    VERIFICATION_CANCELLED: RNPassbaseSDK.VERIFICATION_CANCELLED,
    REQUIRED_OPTION_API_KEY_MISSING: RNPassbaseSDK.REQUIRED_OPTION_API_KEY_MISSING,
    SUCCESS: RNPassbaseSDK.SUCCESS,
    ERROR: RNPassbaseSDK.ERROR
  }
}
