package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksState

data class AppState(
  val goal: Float = 8F,
  val count: Float = 0F
): MavericksState

enum class AppAction {
  Drink,
  Reset
}
