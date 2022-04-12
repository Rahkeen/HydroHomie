package com.rikin.hydrohomie.features.hydration.domain

data class HydrationState(
  val drank: Double = 0.0,
  val goal: Double = 8.0
) {
  val percent = drank / goal
}