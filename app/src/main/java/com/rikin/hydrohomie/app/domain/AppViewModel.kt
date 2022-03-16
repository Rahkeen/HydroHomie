package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel

class AppViewModel(initialState: AppState): MavericksViewModel<AppState>(initialState) {
  fun send(action: AppAction) {
    when(action) {
      AppAction.Drink -> {
        setState {
          val newCount = minOf(count+1, goal)
          copy(count = newCount)
        }
      }
      AppAction.Reset -> {
        setState {
          copy(count = 0F)
        }
      }
    }
  }
}
