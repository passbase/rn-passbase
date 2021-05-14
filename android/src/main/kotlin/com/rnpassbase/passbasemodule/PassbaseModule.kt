package com.rnpassbase.passbasemodule

import android.widget.Toast
import androidx.annotation.Nullable
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.passbase.passbase_sdk.PassbaseSDK
import com.passbase.passbase_sdk.PassbaseSDKListener
import com.rnpassbase.Utils.convertMapToPairArr
import com.rnpassbase.Utils.mapKeysCount

@ReactModule(name = com.rnpassbase.passbasemodule.PassbaseSDK.reactClass)
class PassbaseSDK(context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
  companion object {
    const val reactClass = "RNPassbaseSDK"

    // constants to provide meaningful hints & are also exposed to JS.
    const val ERROR_INITIALIZING_PASSBASE = "error_initializing_passbase_sdk"
    const val INITIALZE_PASSBASE_TO_START_VERIFICATION = "initialize_passbase_sdk_before_trying_verification"
    const val ERROR_START_VERIFICATION = "error_starting_verification"
    const val VERIFICATION_CANCELLED = "verification_cancelled"
    const val REQUIRED_OPTION_API_KEY_MISSING = "required_option_api_key_is_missing"
    const val SUCCESS = "success"
    const val ERROR = "error"

    //reference to passbase instance.
    var passbaseRef: PassbaseSDK? = null
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
  fun initialize (publishableApiKey: String, promise: Promise) {
    try {
      PassbaseSDK.source = 2

      if (passbaseRef == null && currentActivity != null) {
        passbaseRef = PassbaseSDK(currentActivity!!)
        passbaseRef!!.callback(object : PassbaseSDKListener {
              override fun onStart() {
                val map = Arguments.createMap()
                sendEvent(reactApplicationContext, "onStart", map);
              }
    
              override fun onFinish(identityAccessKey: String) {
                val params = Arguments.createMap();
                params.putString("identityAccessKey", identityAccessKey);
                sendEvent(reactApplicationContext, "onFinish", params);
              }

              override fun onSubmitted(identityAccessKey: String) {
                val params = Arguments.createMap();
                params.putString("identityAccessKey", identityAccessKey);
                sendEvent(reactApplicationContext, "onSubmitted", params);
              }
    
              override fun onError(errorCode: String) {
                val params = Arguments.createMap();
                params.putString("errorCode", errorCode);
                sendEvent(reactApplicationContext, "onError", params);
              }
        })
      }

      passbaseRef!!.initialize(publishableApiKey)

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
  // native methods conditionally. can check `src/PassbaseSDK/native-modules.ts`
  @ReactMethod
  fun initWithCB (publishableApiKey: String, onSuccess: Callback, onFailure: Callback) {
    try {
      PassbaseSDK.source = 2

      if (passbaseRef == null && currentActivity != null) {
        passbaseRef = PassbaseSDK(currentActivity!!)
        passbaseRef!!.callback(object : PassbaseSDKListener {
              override fun onStart() {
                val map = Arguments.createMap()
                sendEvent(reactApplicationContext, "onStart", map);
              }
    
              override fun onFinish(identityAccessKey: String) {
                val params = Arguments.createMap();
                params.putString("identityAccessKey", identityAccessKey);
                sendEvent(reactApplicationContext, "onFinish", params);
              }

              override fun onSubmitted(identityAccessKey: String) {
                val params = Arguments.createMap();
                params.putString("identityAccessKey", identityAccessKey);
                sendEvent(reactApplicationContext, "onSubmitted", params);
              }
    
              override fun onError(errorCode: String) {
                val params = Arguments.createMap();
                params.putString("errorCode", errorCode);
                sendEvent(reactApplicationContext, "onError", params);
              }
        })

      }


      passbaseRef!!.initialize(publishableApiKey)

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


  @ReactMethod
  fun setPrefillUserEmail (email: String?, promise: Promise) {
    try {
      if (passbaseRef == null) {
        throw Exception(INITIALZE_PASSBASE_TO_START_VERIFICATION)
      }

      passbaseRef!!.prefillUserEmail = email;
      val map = Arguments.createMap()
      map.putBoolean(SUCCESS, true)
      promise.resolve(map)
    } catch (e: Exception) {
      promise.reject(ERROR_START_VERIFICATION, e)
    }
  }
  
  @ReactMethod
  fun setPrefillCountry (country: String?, promise: Promise) {
    try {
      if (passbaseRef == null) {
        throw Exception(INITIALZE_PASSBASE_TO_START_VERIFICATION)
      }

      passbaseRef!!.prefillCountry = country;
      val map = Arguments.createMap()
      map.putBoolean(SUCCESS, true)
      promise.resolve(map)
    } catch (e: Exception) {
      promise.reject(ERROR_START_VERIFICATION, e)
    }
  }

  @ReactMethod
  fun setMetaData (metaData: String?, promise: Promise) {
    try {
      if (passbaseRef == null) {
        throw Exception(INITIALZE_PASSBASE_TO_START_VERIFICATION)
      }

      passbaseRef!!.metaData = metaData;
      val map = Arguments.createMap()
      map.putBoolean(SUCCESS, true)
      promise.resolve(map)
    } catch (e: Exception) {
      promise.reject(ERROR_START_VERIFICATION, e)
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
  // native methods conditionally. can check `src/PassbaseSDK/native-modules.ts`
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


  // method to send events to JS side.
  private fun sendEvent(reactContext: ReactContext,
                        eventName: String,
                        @Nullable params: WritableMap) {
    reactContext
      .getJSModule(RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
}
