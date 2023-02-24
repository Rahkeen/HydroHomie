package com.rikin.hydrohomie.features.streak.common.domain

import com.rikin.hydrohomie.app.common.domain.Weekday
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState

data class StreakState(
  val currentWeek: List<HydrationState>,
  val currentDay: Weekday
) {

  val consecutiveDays = currentWeek.count { it.drank > 0 }
  val completedDays = currentWeek.count { it.drank == it.goal }
  val totalDrank = currentWeek.sumOf { it.drank }
}

