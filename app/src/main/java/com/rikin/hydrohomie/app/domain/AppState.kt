package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksState
import com.rikin.hydrohomie.features.hydration.domain.HydrationState

data class AppState(
  val dayOfWeek: Int = 7,
  val hydrationWeek: List<HydrationState> = listOf(
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState(count = 8f),
    HydrationState()
  ),
) : MavericksState {
  val currentHydration = hydrationWeek[dayOfWeek - 1]
}

enum class AppAction {
  Drink,
  Reset,
  Streaks
}
