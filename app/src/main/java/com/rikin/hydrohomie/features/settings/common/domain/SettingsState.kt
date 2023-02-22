package com.rikin.hydrohomie.features.settings.common.domain

data class SettingsState(
  val personalGoal: Int = 64,
  val drinkSizes: List<DrinkSizeState> = DrinkSizes,
  val defaultDrinkSize: Int = 8,
)

val DrinkSizes = listOf(
  DrinkSizeState(position = 0, amount = 8, selected = true),
  DrinkSizeState(position = 1, amount = 16, selected = false),
  DrinkSizeState(position = 2, amount = 32, selected = false),
)

data class DrinkSizeState(
  val position: Int,
  val amount: Int,
  val selected: Boolean
) {
  val label = "${amount}oz"
}
