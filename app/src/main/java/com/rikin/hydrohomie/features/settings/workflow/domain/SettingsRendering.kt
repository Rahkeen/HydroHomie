package com.rikin.hydrohomie.features.settings.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.rikin.hydrohomie.features.settings.mavericks.domain.SettingsState

data class SettingsRendering(
  val state: SettingsState,
  val actions: (AppAction) -> Unit
)
