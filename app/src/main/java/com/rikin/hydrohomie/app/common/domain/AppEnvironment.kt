package com.rikin.hydrohomie.app.common.domain

import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.drinks.DrinkRepository

data class AppEnvironment(
  val drinkRepository: DrinkRepository,
  val dates: Dates,
)
