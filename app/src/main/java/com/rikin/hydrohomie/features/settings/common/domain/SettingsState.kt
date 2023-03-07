package com.rikin.hydrohomie.features.settings.common.domain

data class SettingsState(
  val personalGoal: Int,
  val defaultDrinkSize: Int,
  val drinkSizes: List<DrinkSizeState> = DrinkSizes,
)

val DrinkSizes = listOf(
  DrinkSizeState(position = 0, amount = 8, selected = true),
  DrinkSizeState(position = 1, amount = 16, selected = false),
  DrinkSizeState(position = 2, amount = 32, selected = false),
  DrinkSizeState(position = 3, amount = 0, selected = false, custom = true),
)

data class DrinkSizeState(
  val position: Int,
  val amount: Int,
  val selected: Boolean,
  val custom: Boolean = false
) {
  val label = "$amount oz"
}
