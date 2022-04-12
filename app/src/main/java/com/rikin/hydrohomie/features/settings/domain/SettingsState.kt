package com.rikin.hydrohomie.features.settings.domain

data class SettingsState(
  val drinkSize: Double = 8.0,
  val personalGoal: Double = 64.0,
  val userImageUrl: String? = null
)