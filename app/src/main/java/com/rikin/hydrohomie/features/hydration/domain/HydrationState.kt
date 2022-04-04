package com.rikin.hydrohomie.features.hydration.domain

data class HydrationState(
  val count: Float = 0F,
  val goal: Float = 8F
) {
  val percent = count / goal
}