package com.rikin.hydrohomie.app.platform

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.workflow.domain.AppWorkflow
import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.dates.RealDates
import com.rikin.hydrohomie.drinks.DrinkRepository
import com.rikin.hydrohomie.drinks.RealDrinkRepository
import com.rikin.hydrohomie.settings.RealSettingsRepository
import com.rikin.hydrohomie.settings.SettingsRepository
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import java.time.format.DateTimeFormatter

@WorkflowUiExperimentalApi
class HydroHomieApplication : Application() {

  lateinit var drinkRepository: DrinkRepository
  lateinit var settingsRepository: SettingsRepository
  lateinit var appWorkflow: AppWorkflow
  val dates: Dates = RealDates(formatter = DateTimeFormatter.ofPattern(DATE_PATTERN))

  override fun onCreate() {
    super.onCreate()

    Mavericks.initialize(this)
    FirebaseApp.initializeApp(this)
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)

    drinkRepository = RealDrinkRepository(Firebase.firestore)
    settingsRepository = RealSettingsRepository(Firebase.firestore)
    appWorkflow = AppWorkflow(
      environment = AppEnvironment(
        dates = dates,
        drinkRepository = drinkRepository,
        settingsRepository = settingsRepository,
      ),
    )
  }
}

const val DATE_PATTERN = "MM-dd-yyyy"