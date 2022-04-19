package com.rikin.hydrohomie.features.settings.workflow.domain

import com.rikin.hydrohomie.features.settings.common.domain.SettingsState

sealed class SettingsOutput {
  data class UpdateState(val state: SettingsState) : SettingsOutput()
}