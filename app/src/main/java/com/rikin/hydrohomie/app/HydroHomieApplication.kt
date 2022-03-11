package com.rikin.hydrohomie.app

import android.app.Application
import com.airbnb.mvrx.Mavericks

class HydroHomieApplication: Application() {
  override fun onCreate() {
    super.onCreate()

    Mavericks.initialize(this)
  }
}