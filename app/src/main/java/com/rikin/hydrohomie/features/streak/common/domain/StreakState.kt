package com.rikin.hydrohomie.features.streak.common.domain

import com.rikin.hydrohomie.app.common.domain.Weekday
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState

data class StreakState(
  val currentWeek: List<HydrationState>,
  val currentDay: Weekday
)
