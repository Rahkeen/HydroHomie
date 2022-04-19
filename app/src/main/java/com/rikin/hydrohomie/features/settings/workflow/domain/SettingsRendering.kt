package com.rikin.hydrohomie.features.settings.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState

data class SettingsRendering(
    val state: AppState,
    val actions: (AppAction) -> Unit
)
