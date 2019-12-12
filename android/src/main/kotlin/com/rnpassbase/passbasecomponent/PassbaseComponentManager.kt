package com.rnpassbase.passbasecomponent

import android.graphics.Color
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

@ReactModule(name = PassbaseComponentManager.reactClass)
class PassbaseComponentManager : SimpleViewManager<PassbaseComponent>() {

  companion object {
    const val reactClass = "RNPassbaseComponent"
  }

  override fun getName(): String {
    return reactClass
  }

  override fun createViewInstance(reactContext: ThemedReactContext): PassbaseComponent {
    return PassbaseComponent(reactContext)
  }

  @ReactProp(name = "color", customType = "Color", defaultInt = Color.RED)
  fun setColor(view: PassbaseComponent, color: Int) {
    view.setColor(color)
  }
}
