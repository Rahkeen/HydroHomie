package com.rikin.hydrohomie.features.streak.common.domain

import com.rikin.hydrohomie.app.common.domain.Weekday
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState

data class StreakState(
  val currentWeek: List<HydrationState>,
  val currentDay: Weekday
) {

  val consecutiveDays = consecutiveDaysDrank()
  val completedDays = currentWeek.count { it.drank == it.goal }
  val totalDrank = currentWeek.sumOf { it.drank }


  private fun consecutiveDaysDrank(): Int {
    var days = 0
    currentWeek
      .subList(0, currentDay.ordinal)
      .forEach { if (it.drank > 0) days++ else days = 0 }
    return days
  }
}

