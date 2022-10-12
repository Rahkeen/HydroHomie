package com.rikin.hydrohomie.settings

interface SettingsRepository {
  suspend fun getSettings(): LocalSettings
  suspend fun insertSettings(localSettings: LocalSettings)
  suspend fun updateSettings(localSettings: LocalSettings)
}

class FakeSettingsRepository : SettingsRepository {
  override suspend fun getSettings(): LocalSettings {
    return LocalSettings(drinkSize = 8, goal = 64, userImageUrl = "")
  }

  override suspend fun insertSettings(localSettings: LocalSettings) {
  }

  override suspend fun updateSettings(localSettings: LocalSettings) {
  }
}
