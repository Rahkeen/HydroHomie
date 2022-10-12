package com.rikin.hydrohomie.features.hydration.common.domain

data class HydrationState(
  val drank: Int = 0,
  val goal: Int = 64,
  val drinkAmount: Int = 8
) {
  val percent = drank.toFloat() / goal
}