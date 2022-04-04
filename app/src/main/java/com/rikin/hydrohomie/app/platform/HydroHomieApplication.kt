package com.rikin.hydrohomie.app.platform

import android.app.Application
import com.airbnb.mvrx.Mavericks
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class HydroHomieApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    Mavericks.initialize(this)
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
  }
}