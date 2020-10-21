package com.rnpassbase.passbasecomponent

import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.passbase.passbase_sdk.PassbaseButton
import com.rnpassbase.passbasemodule.PassbaseSDK

@ReactModule(name = PassbaseComponentManager.reactClass)
class PassbaseComponentManager : SimpleViewManager<PassbaseButton>() {

  companion object {
    const val reactClass = "RNPassbaseComponent"
  }

  override fun getName(): String {
    return reactClass
  }

  override fun createViewInstance(reactContext: ThemedReactContext): PassbaseButton {
    val passbaseButton: PassbaseButton = PassbaseButton(reactContext)

    passbaseButton.setOnClickListener {
      try {
        if (PassbaseSDK.passbaseRef !== null) {
          PassbaseSDK.passbaseRef!!.startVerification()
        } else {
          Toast.makeText(reactContext, "Passbase Module must be initialized before trying to start verification", Toast.LENGTH_LONG).show()
          print("Passbase Module must be initialized before trying to start verification")
        }
      } catch (ex: Exception) {
        Toast.makeText(reactContext, ex.message, Toast.LENGTH_LONG).show()
        print(ex)
      }
    }
    return passbaseButton
  }
}
