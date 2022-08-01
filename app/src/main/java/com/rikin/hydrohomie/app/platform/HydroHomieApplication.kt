package com.rikin.hydrohomie.app.platform

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.airbnb.mvrx.Mavericks
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.jobs.NotificationWorker
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
import java.util.concurrent.TimeUnit

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
    createNotificationChannel(this)

    WorkManager.getInstance(this).enqueue(
      PeriodicWorkRequestBuilder<NotificationWorker>(
        15,
        TimeUnit.MINUTES
      ).build()
    )
  }
}

private fun createNotificationChannel(context: Context) {
  // Create the NotificationChannel, but only on API 26+ because
  // the NotificationChannel class is new and not in the support library
  val name = "Reminders"
  val descriptionText = "Reminders to drink water"
  val importance = NotificationManager.IMPORTANCE_DEFAULT
  val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
    description = descriptionText
  }
  // Register the channel with the system
  val notificationManager = context.getSystemService(NotificationManager::class.java)
  notificationManager.createNotificationChannel(channel)
}

const val CHANNEL_ID = "com.rikin.hydrohomie.REMINDER_NOTIFICATIONS"
const val DATE_PATTERN = "MM-dd-yyyy"