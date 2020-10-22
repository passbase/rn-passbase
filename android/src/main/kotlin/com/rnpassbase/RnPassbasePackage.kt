package com.rnpassbase

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

import com.rnpassbase.passbasemodule.PassbaseSDK
import com.rnpassbase.passbasecomponent.PassbaseComponentManager

class RnPassbasePackage : ReactPackage {
  override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
    return listOf(
      PassbaseSDK(reactContext)
    )
  }

  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(
      PassbaseComponentManager()
    )
  }
}
