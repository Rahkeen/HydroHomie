package com.rikin.hydrohomie.features.settings.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppState

sealed class SettingsOutput {
    data class UpdateState(val state: AppState) : SettingsOutput()
}