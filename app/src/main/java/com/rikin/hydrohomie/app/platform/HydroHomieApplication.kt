package com.rikin.hydrohomie.app.platform

import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.airbnb.mvrx.Mavericks
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.data.AppDatabase
import com.rikin.hydrohomie.app.data.DATABASE_NAME
import com.rikin.hydrohomie.app.jobs.Notifier
import com.rikin.hydrohomie.app.jobs.RealNotifier
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

@WorkflowUiExperimentalApi
class HydroHomieApplication : Application() {

  private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  val dates: Dates = RealDates(formatter = DateTimeFormatter.ofPattern(DATE_PATTERN))

  private lateinit var appDatabase: AppDatabase
  lateinit var drinkRepository: DrinkRepository
  lateinit var settingsRepository: SettingsRepository
  lateinit var appWorkflow: AppWorkflow
  lateinit var notifier: Notifier

  @OptIn(ExperimentalAnimationApi::class)
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
    notifier = RealNotifier(applicationContext)
    drinkRepository = LocalDrinkRepository(appDatabase.localDrinkDao())
    settingsRepository = LocalSettingsRepository(appDatabase.localSettingsDao())
    appWorkflow = AppWorkflow(
      environment = AppEnvironment(
        dates = dates,
        drinkRepository = drinkRepository,
        settingsRepository = settingsRepository,
        notifier = notifier
      ),
    )
  }

  private fun seedSettingsData() {
    applicationScope.launch {
      appDatabase.localSettingsDao().insertSettings(
        LocalSettings(
          drinkSize = 16,
          goal = 64,
          notificationsEnabled = false,
          onboarded = false
        )
      )
    }
  }
}

const val DATE_PATTERN = "MM-dd-yyyy"