package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksState
import com.rikin.hydrohomie.features.hydration.domain.HydrationState
import com.rikin.hydrohomie.features.streak.domain.StreakState

data class AppState(
  val dayOfWeek: Int = 7,
  val hydrationWeek: List<HydrationState> = listOf(
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState(),
    HydrationState()
  ),
) : MavericksState {
  val currentHydration = hydrationWeek[dayOfWeek - 1]
  val streakState = StreakState(
    currentWeek = hydrationWeek,
    currentDay = dayOfWeek
  )
}

enum class AppAction {
  Drink,
  Reset,
  Streaks
}
