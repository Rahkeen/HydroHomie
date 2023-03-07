package com.rikin.hydrohomie.features.hydration.common.domain

data class HydrationState(
  val drank: Int,
  val goal: Int,
  val drinkAmount: Int = 8
) {
  val percent = drank.toFloat() / goal
}