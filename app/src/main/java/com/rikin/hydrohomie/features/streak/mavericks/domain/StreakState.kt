package com.rikin.hydrohomie.features.streak.mavericks.domain

import com.rikin.hydrohomie.app.mavericks.domain.Weekday
import com.rikin.hydrohomie.features.hydration.mavericks.domain.HydrationState

data class StreakState(
  val currentWeek: List<HydrationState>,
  val currentDay: Weekday
)
