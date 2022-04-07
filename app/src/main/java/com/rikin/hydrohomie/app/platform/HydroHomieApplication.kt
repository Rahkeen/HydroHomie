package com.rikin.hydrohomie.app.platform

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rikin.hydrohomie.dates.Dates
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import java.time.format.DateTimeFormatter

class HydroHomieApplication : Application() {

  lateinit var store: FirebaseFirestore
  val dates = Dates(formatter = DateTimeFormatter.ofPattern(DATE_PATTERN))

  override fun onCreate() {
    super.onCreate()

    Mavericks.initialize(this)
    FirebaseApp.initializeApp(this)
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)

    store = Firebase.firestore
  }
}

private const val DATE_PATTERN = "MM-dd-yyyy"