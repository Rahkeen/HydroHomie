package com.rikin.hydrohomie.app.domain

import com.rikin.hydrohomie.dates.RealDates
import com.rikin.hydrohomie.drinkrepo.DrinkRepository

data class AppEnvironment(
  val store: DrinkRepository,
  val dates: RealDates,
)
