package com.rikin.hydrohomie.app.platform

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.airbnb.mvrx.Mavericks
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.data.AppDatabase
import com.rikin.hydrohomie.app.data.DATABASE_NAME
import com.rikin.hydrohomie.app.jobs.NotificationWorker
import com.rikin.hydrohomie.app.workflow.domain.AppWorkflow
import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.dates.RealDates
import com.rikin.hydrohomie.drinks.DrinkRepository
import com.rikin.hydrohomie.drinks.LocalDrinkRepository
import com.rikin.hydrohomie.settings.LocalSettings
import com.rikin.hydrohomie.settings.LocalSettingsRepository
import com.rikin.hydrohomie.settings.SettingsRepository
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@WorkflowUiExperimentalApi
class HydroHomieApplication : Application() {

  private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  private lateinit var appDatabase: AppDatabase
  lateinit var drinkRepository: DrinkRepository
  lateinit var settingsRepository: SettingsRepository
  lateinit var appWorkflow: AppWorkflow
  val dates: Dates = RealDates(formatter = DateTimeFormatter.ofPattern(DATE_PATTERN))

  override fun onCreate() {
    super.onCreate()

    Mavericks.initialize(this)
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)

    appDatabase = Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java,
      DATABASE_NAME
    ).addCallback(
      object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
          seedSettingsData()
        }
      }
    ).build()

    drinkRepository = LocalDrinkRepository(appDatabase.localDrinkDao())
    settingsRepository = LocalSettingsRepository(appDatabase.localSettingsDao())
    appWorkflow = AppWorkflow(
      environment = AppEnvironment(
        dates = dates,
        drinkRepository = drinkRepository,
        settingsRepository = settingsRepository,
      ),
    )

    createNotificationChannel(this)

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
      "Drink Water",
      ExistingPeriodicWorkPolicy.KEEP,
      PeriodicWorkRequestBuilder<NotificationWorker>(
        1,
        TimeUnit.HOURS
      ).build()
    )
  }

  private fun seedSettingsData() {
    applicationScope.launch {
      appDatabase.localSettingsDao().insertSettings(
        LocalSettings(
          drinkSize = 8,
          goal = 8
        )
      )
    }
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