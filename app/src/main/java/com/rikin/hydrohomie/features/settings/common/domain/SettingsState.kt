package com.rikin.hydrohomie.features.settings.common.domain

data class SettingsState(
  val drinkAmount: Double = 8.0,
  val personalGoal: Double = 64.0,
  val userImageUrl: String? = null
)