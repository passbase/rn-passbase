import { NativeModules } from 'react-native'
const { RNPassbaseModule } = NativeModules

const init = async (publishableApiKey: string, onSuccess: Function, onFailure: Function) => {
  const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
    (onFailure && typeof onFailure === 'function')

  try {

    if (!publishableApiKey) {
      throw new Error(RNPassbaseModule.REQUIRED_OPTION_API_KEY_MISSING)
    }

    if (isCallbackBased) {
      return RNPassbaseModule.initWithCB(publishableApiKey, onSuccess, onFailure)
    } else {
      return RNPassbaseModule.initialize(publishableApiKey)
    }

  } catch (ex) {
    if (isCallbackBased) {
      onFailure(ex)

    } else {
      return Promise.reject(ex)

    }
  }
}

const setPrefillUserEmail = (email: string) => RNPassbaseModule.setPrefillUserEmail(email);

const startVerification = async (onSuccess: Function, onFailure: Function) => {
  // todo: make sure to chekc internet connection as verificaiton doesn't start without internet.
  const isCallbackBased = (onSuccess && typeof onSuccess === 'function') ||
    (onFailure && typeof onFailure === 'function')

  if (isCallbackBased) {
    return RNPassbaseModule.startVerificationWithCB(onSuccess, onFailure)
  }
  return RNPassbaseModule.startVerification()
}
const show = (message: string) => RNPassbaseModule.show(message);

export const NativeModule = {
  ...RNPassbaseModule,
  init,
  startVerification,
  show,
  setPrefillUserEmail,
  constants: {
    ERROR_INITIALIZING_PASSBASE: RNPassbaseModule.ERROR_INITIALIZING_PASSBASE,
    INITIALZE_PASSBASE_TO_START_VERIFICATION: RNPassbaseModule.INITIALZE_PASSBASE_TO_START_VERIFICATION,
    ERROR_START_VERIFICATION: RNPassbaseModule.ERROR_START_VERIFICATION,
    VERIFICATION_CANCELLED: RNPassbaseModule.VERIFICATION_CANCELLED,
    REQUIRED_OPTION_API_KEY_MISSING: RNPassbaseModule.REQUIRED_OPTION_API_KEY_MISSING,
    SUCCESS: RNPassbaseModule.SUCCESS,
    ERROR: RNPassbaseModule.ERROR
  }
}
