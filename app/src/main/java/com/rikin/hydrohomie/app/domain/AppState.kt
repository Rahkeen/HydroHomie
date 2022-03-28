package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksState

data class AppState(
  val dayOfWeek: Int = 7,
  val weeklyHydration: List<HydrationState> = listOf(
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState()
  ),
): MavericksState {
  val currentHydration = weeklyHydration[dayOfWeek-1]
  val streaks = StreakState(
    currentStreak = 6,
    bestStreak = 10,
    currentWeek = weeklyHydration
  )
}

data class HydrationState(
  val count: Float = 0F,
  val goal: Float = 8F
)

data class StreakState(
  val currentStreak: Int = 0,
  val bestStreak: Int = 0,
  val currentWeek: List<HydrationState> = listOf()
)

enum class AppAction {
  Drink,
  Reset,
  Streaks
}
