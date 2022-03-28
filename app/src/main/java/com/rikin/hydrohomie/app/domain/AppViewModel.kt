package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel

class AppViewModel(initialState: AppState) : MavericksViewModel<AppState>(initialState) {
  fun send(action: AppAction) {
    when (action) {
      AppAction.Drink -> {
        setState {
          copy(
            weeklyHydration = List(weeklyHydration.size) { index ->
              if (index == dayOfWeek-1) {
                weeklyHydration[index].copy(count = weeklyHydration[index].count + 1)
              } else {
                weeklyHydration[index]
              }
            }
          )
        }
      }
      AppAction.Reset -> {
        setState {
          copy(
            weeklyHydration = List(weeklyHydration.size) { index ->
              if (index == dayOfWeek-1) {
                HydrationState()
              } else {
                weeklyHydration[index]
              }
            }
          )
        }
      }
      AppAction.Streaks -> {

      }
    }
  }
}
