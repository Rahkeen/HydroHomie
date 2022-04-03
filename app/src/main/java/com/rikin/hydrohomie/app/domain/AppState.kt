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
}

data class HydrationState(
  val count: Float = 0F,
  val goal: Float = 8F
)

data class StreakState(
  val currentWeek: List<HydrationState>
)

enum class AppAction {
  Drink,
  Reset,
  Streaks
}
