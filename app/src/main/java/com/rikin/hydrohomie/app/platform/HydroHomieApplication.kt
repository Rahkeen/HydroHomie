package com.rikin.hydrohomie.app.platform

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.dates.RealDates
import com.rikin.hydrohomie.drinks.DrinkRepository
import com.rikin.hydrohomie.drinks.RealDrinkRepository
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import java.time.format.DateTimeFormatter

@WorkflowUiExperimentalApi
class HydroHomieApplication : Application() {

  lateinit var drinkRepository: DrinkRepository
  val dates: Dates = RealDates(formatter = DateTimeFormatter.ofPattern(DATE_PATTERN))

  override fun onCreate() {
    super.onCreate()

    Mavericks.initialize(this)
    FirebaseApp.initializeApp(this)
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)

    drinkRepository = RealDrinkRepository(Firebase.firestore)
  }
}

const val DATE_PATTERN = "MM-dd-yyyy"