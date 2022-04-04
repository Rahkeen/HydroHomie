package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel
import com.rikin.hydrohomie.features.hydration.domain.HydrationState

class AppViewModel(initialState: AppState) : MavericksViewModel<AppState>(initialState) {
  fun send(action: AppAction) {
    when (action) {
      AppAction.Drink -> {
        setState {
          copy(
            hydrationWeek = List(hydrationWeek.size) { index ->
              if (index == dayOfWeek - 1) {
                hydrationWeek[index].copy(count = hydrationWeek[index].count + 1)
              } else {
                hydrationWeek[index]
              }
            }
          )
        }
      }
      AppAction.Reset -> {
        setState {
          copy(
            hydrationWeek = List(hydrationWeek.size) { index ->
              if (index == dayOfWeek - 1) {
                HydrationState()
              } else {
                hydrationWeek[index]
              }
            }
          )
        }
      }
      AppAction.Streaks -> {}
    }
  }
}
