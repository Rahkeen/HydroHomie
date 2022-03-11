package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksState

data class AppState(
  val greeting: String = ""
): MavericksState
