package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksState

data class AppState(
  val dayOfWeek: Int = 7,
  val weeklyHydration: List<HydrationState> = listOf(
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState()
  ),
): MavericksState {
  val currentHydration = weeklyHydration[dayOfWeek-1]
}

data class HydrationState(
  val count: Float = 0F,
  val goal: Float = 8F
) {
  val percent = count / goal
}

enum class AppAction {
  Drink,
  Reset,
  Streaks
}
