package com.rikin.hydrohomie.app.platform

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class HydroHomieApplication : Application() {

  lateinit var store: FirebaseFirestore

  override fun onCreate() {
    super.onCreate()

    Mavericks.initialize(this)
    FirebaseApp.initializeApp(this)
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)

    store = Firebase.firestore
  }
}