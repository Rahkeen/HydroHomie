package com.rikin.hydrohomie.features.settings.common.domain


data class SettingsState(
  val personalGoal: Int,
  val defaultDrinkSize: Int,
  val notificationsEnabled: Boolean
) {
  val drinkSizes: List<DrinkSizeState> = if (defaultDrinkSize in StandardSizes) {
    DrinkSizes.map { state ->
      state.copy(selected = state.amount == defaultDrinkSize)
    }
  } else {
    DrinkSizes.map { state ->
      if (state.custom) {
        state.copy(selected = true, amount = defaultDrinkSize)
      } else {
        state.copy(selected = false)
      }
    }
  }
}

data class DrinkSizeState(
  val position: Int,
  val amount: Int,
  val selected: Boolean,
  val custom: Boolean = false
) {
  val label = "$amount oz"
}

private val StandardSizes = setOf(8, 16, 32)

private val DrinkSizes = listOf(
  DrinkSizeState(position = 0, amount = 8, selected = true),
  DrinkSizeState(position = 1, amount = 16, selected = false),
  DrinkSizeState(position = 2, amount = 32, selected = false),
  DrinkSizeState(position = 3, amount = 0, selected = false, custom = true),
)

