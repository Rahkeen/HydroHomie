package com.rikin.hydrohomie.features.settings.common.domain

data class SettingsState(
  val drinkAmount: Int = 8,
  val personalGoal: Int = 64,
  val userImageUrl: String? = null
)