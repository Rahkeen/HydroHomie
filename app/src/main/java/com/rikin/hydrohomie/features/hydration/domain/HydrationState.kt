package com.rikin.hydrohomie.features.hydration.domain

data class HydrationState(
  val drank: Double = 0.0,
  val goal: Double = 64.0
) {
  val percent = drank / goal
}