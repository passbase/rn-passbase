package com.rnpassbase.passbasemodule

import android.graphics.Color
import android.widget.Toast
import androidx.annotation.Nullable
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.passbase.passbase_sdk.Passbase
import com.rnpassbase.Utils.convertMapToPairArr
import com.rnpassbase.Utils.mapKeysCount

@ReactModule(name = PassbaseModule.reactClass)
class PassbaseModule(context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
  companion object {
    const val reactClass = "RNPassbaseModule"

    // constants to provide meaningful hints & are also exposed to JS.
    const val ERROR_INITIALIZING_PASSBASE = "error_initializing_passbase_sdk"
    const val INITIALZE_PASSBASE_TO_START_VERIFICATION = "initialize_passbase_sdk_before_trying_verification"
    const val ERROR_START_VERIFICATION = "error_starting_verification"
    const val VERIFICATION_CANCELLED = "verification_cancelled"
    const val REQUIRED_OPTION_API_KEY_MISSING = "required_option_api_key_is_missing"
    const val SUCCESS = "success"
    const val ERROR = "error"

    //reference to passbase instance.
    var passbaseRef: Passbase? = null
  }
  // return name of the module to be used in JS side.
  override fun getName(): String {
    return reactClass
  }

  // return constants that can be used in JS side e.g. ModuleName.ERROR
  override fun getConstants(): Map<String, Any>? {
    val constants: MutableMap<String, Any> = HashMap()
    constants["REQUIRED_OPTION_API_KEY_MISSING"] = REQUIRED_OPTION_API_KEY_MISSING
    constants["ERROR_INITIALIZING_PASSBASE"] = ERROR_INITIALIZING_PASSBASE
    constants["INITIALZE_PASSBASE_TO_START_VERIFICATION"] = INITIALZE_PASSBASE_TO_START_VERIFICATION
    constants["ERROR_START_VERIFICATION"] = ERROR_START_VERIFICATION
    constants["VERIFICATION_CANCELLED"] = VERIFICATION_CANCELLED
    constants["SUCCESS"] = SUCCESS
    constants["ERROR"] = ERROR
    return constants
  }

  // Promise based implementation of init method
  @ReactMethod
  fun initialize (apiKey: String, email: String, additionalAttribs: ReadableMap, promise: Promise) {
    try {
      Passbase.source = 2

      if (passbaseRef == null && currentActivity != null) {
        passbaseRef = Passbase(currentActivity!!)
        passbaseRef!!.onCancelPassbaseVerification {
          val map = Arguments.createMap()
          sendEvent(reactApplicationContext, "onCancelPassbase", map);
        }

        passbaseRef!!.onCompletePassbaseVerification { authKey ->
          val params = Arguments.createMap();
          params.putString("authKey", authKey);
          sendEvent(reactApplicationContext, "onCompletePassbase", params);
        }

      }

      val hasAdditionalAttributes = mapKeysCount(additionalAttribs) != 0
      val hasEmail = !email.isEmpty()
      if (!hasAdditionalAttributes && hasEmail) {
        passbaseRef!!.initialize(apiKey, email)

      } else if (!hasAdditionalAttributes && !hasEmail)  {
        passbaseRef!!.initialize(apiKey)

      } else if (hasAdditionalAttributes && hasEmail) {
        val additionAttribArr = convertMapToPairArr(additionalAttribs)
        passbaseRef!!.initialize(apiKey, email, additionAttribArr)

      }

      val map = Arguments.createMap()
      map.putBoolean(SUCCESS, true)
      promise.resolve(map)
    } catch (e: Exception) {
      promise.reject(ERROR_INITIALIZING_PASSBASE, e)
    }
  }

  // callback based implementation of init method.
  // here had to change name because unfortunately method overload wasn't working
  // due to some unknown reasons. so to avoid I've changed name of this & in JS I kept single name & called
  // native methods conditionally. can check `src/PassbaseModule/native-modules.ts`
  @ReactMethod
  fun initWithCB (apiKey: String, email: String, additionalAttribs: ReadableMap, onSuccess: Callback, onFailure: Callback) {
    try {
      Passbase.source = 2

      if (passbaseRef == null && currentActivity != null) {
        passbaseRef = Passbase(currentActivity!!)
        passbaseRef!!.onCancelPassbaseVerification {
          val map = Arguments.createMap()
          sendEvent(reactApplicationContext, "onCancelPassbase", map);
        }

        passbaseRef!!.onCompletePassbaseVerification { authKey ->
          val params = Arguments.createMap();
          params.putString("authKey", authKey);
          sendEvent(reactApplicationContext, "onCompletePassbase", params);
        }

      }

      val hasAdditionalAttributes = mapKeysCount(additionalAttribs) != 0
      val hasEmail = !email.isEmpty()
      if (!hasAdditionalAttributes && !hasEmail)  {
        passbaseRef!!.initialize(apiKey)

      } else if (!hasAdditionalAttributes && hasEmail) {
        passbaseRef!!.initialize(apiKey, email)

      } else if (hasAdditionalAttributes && hasEmail) {
        val additionAttribArr = convertMapToPairArr(additionalAttribs)
        passbaseRef!!.initialize(apiKey, email, additionAttribArr)
      }

      val map = Arguments.createMap()
      map.putBoolean(SUCCESS, true)
      onSuccess.invoke(map)
    } catch (e: Exception) {
      val map = Arguments.createMap()
      map.putString(ERROR_INITIALIZING_PASSBASE, e.message)
      map.putString("message", e.message)
      onFailure.invoke(map)
    }
  }

  // promise based implementation of startVerification method.
  @ReactMethod
  fun startVerification (promise: Promise) {
    try {
      if (passbaseRef == null) {
        throw Exception(INITIALZE_PASSBASE_TO_START_VERIFICATION)
      }

      passbaseRef!!.startVerification()
      val map = Arguments.createMap()
      map.putBoolean(SUCCESS, true)
      promise.resolve(map)
    } catch (e: Exception) {
      promise.reject(ERROR_START_VERIFICATION, e)
    }
  }

  // callback based implementation of startVerification method.
  // here had to change name because unfortunately method overload wasn't working
  // due to some unknown reasons. so to avoid I've changed name of this & in JS I kept single name & called
  // native methods conditionally. can check `src/PassbaseModule/native-modules.ts`
  @ReactMethod
  fun startVerificationWithCB (onSuccess: Callback, onFailure: Callback) {
    try {
      if (passbaseRef == null) {
        throw Exception(INITIALZE_PASSBASE_TO_START_VERIFICATION)
      }

      passbaseRef!!.startVerification()
      val map = Arguments.createMap()
      map.putBoolean(SUCCESS, true)
      onSuccess.invoke(map)
    } catch (e: Exception) {
      onFailure.invoke(ERROR_START_VERIFICATION, e)
    }
  }

  @ReactMethod
  fun setLoadingIndicatorColor (loadingIndicatorColor: String) {
    try {
      if (!loadingIndicatorColor.isEmpty() && passbaseRef != null) {
        passbaseRef!!.setLoadingIndicatorColor(Color.parseColor(loadingIndicatorColor))
      }
    } catch (ex: Exception) {
      Toast.makeText(reactApplicationContext, ex.message, Toast.LENGTH_LONG).show()
    }
  }

  @ReactMethod
  fun setActionButtonBgColor (actionButtonBgColor: String) {
    try {
      if (!actionButtonBgColor.isEmpty() && passbaseRef != null) {
        passbaseRef!!.setActionButtonBgColor(Color.parseColor(actionButtonBgColor))
      }
    } catch (ex: Exception) {
      Toast.makeText(reactApplicationContext, ex.message, Toast.LENGTH_LONG).show()
    }
  }

  @ReactMethod
  fun setActionButtonDeactivatedBgColor (actionButtonDeactivatedBgColor: String) {
    try {
      if (!actionButtonDeactivatedBgColor.isEmpty() && passbaseRef != null) {
        passbaseRef!!.setActionButtonDeactivatedBgColor(Color.parseColor(actionButtonDeactivatedBgColor))
      }
    } catch (ex: Exception) {
      Toast.makeText(reactApplicationContext, ex.message, Toast.LENGTH_LONG).show()
    }
  }

  @ReactMethod
  fun setActionButtonTextColor (actionButtonTextColor: String) {
    try {
      if (!actionButtonTextColor.isEmpty() && passbaseRef != null) {
        passbaseRef!!.setActionButtonTextColor(Color.parseColor(actionButtonTextColor))
      }
    } catch (ex: Exception) {
      Toast.makeText(reactApplicationContext, ex.message, Toast.LENGTH_LONG).show()
    }
  }

  @ReactMethod
  fun setDisclaimerTextColor (disclaimerTextColor: String) {
    try {
      if (!disclaimerTextColor.isEmpty() && passbaseRef != null) {
        passbaseRef!!.setDisclaimerTextColor(Color.parseColor(disclaimerTextColor))
      }
    } catch (ex: Exception) {
      Toast.makeText(reactApplicationContext, ex.message, Toast.LENGTH_LONG).show()
    }
  }

  @ReactMethod
  fun setTitleTextColor (titleTextColor: String) {
    try {
      if (!titleTextColor.isEmpty() && passbaseRef != null) {
        passbaseRef!!.setTitleTextColor(Color.parseColor(titleTextColor))
      }
    } catch (ex: Exception) {
      Toast.makeText(reactApplicationContext, ex.message, Toast.LENGTH_LONG).show()
    }
  }

  @ReactMethod
  fun setSubtitleTextColor (subtitleTextColor: String) {
    try {
      if (!subtitleTextColor.isEmpty() && passbaseRef != null) {
        passbaseRef!!.setSubtitleTextColor(Color.parseColor(subtitleTextColor))
      }
    } catch (ex: Exception) {
      Toast.makeText(reactApplicationContext, ex.message, Toast.LENGTH_LONG).show()
    }
  }

  // method to send events to JS side.
  private fun sendEvent(reactContext: ReactContext,
                        eventName: String,
                        @Nullable params: WritableMap) {
    reactContext
            .getJSModule(RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
  }
}
