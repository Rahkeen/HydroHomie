package com.rikin.hydrohomie.features.streak.domain

import com.rikin.hydrohomie.features.hydration.domain.HydrationState

data class StreakState(
  val currentWeek: List<HydrationState>,
  val currentDay: Int
)
