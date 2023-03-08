package com.rikin.hydrohomie.app.common.domain

import com.rikin.hydrohomie.app.jobs.Notifier
import com.rikin.hydrohomie.app.jobs.RealNotifier
import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.drinks.DrinkRepository
import com.rikin.hydrohomie.settings.SettingsRepository

data class AppEnvironment(
  val drinkRepository: DrinkRepository,
  val settingsRepository: SettingsRepository,
  val dates: Dates,
  val notifier: Notifier
)
