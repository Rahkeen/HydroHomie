package com.rikin.hydrohomie.features.settings.workflow.domain

import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.features.settings.common.domain.SettingsState

data class SettingsRendering(
  val state: SettingsState,
  val actions: (AppAction) -> Unit
)
