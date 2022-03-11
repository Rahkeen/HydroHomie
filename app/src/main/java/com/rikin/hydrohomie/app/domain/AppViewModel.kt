package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel

class AppViewModel(initialState: AppState): MavericksViewModel<AppState>(initialState) {
  init {
    setState {
      copy(greeting = "Hydro Homie")
    }
  }
}