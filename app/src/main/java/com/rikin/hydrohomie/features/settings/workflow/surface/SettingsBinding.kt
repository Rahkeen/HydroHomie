package com.rikin.hydrohomie.features.settings.workflow.surface

import com.rikin.hydrohomie.features.settings.common.surface.Settings
import com.rikin.hydrohomie.features.settings.workflow.domain.SettingsRendering
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.compose.composeViewFactory

@WorkflowUiExperimentalApi
val SettingsBinding = composeViewFactory<SettingsRendering> { rendering, _ ->
  Settings(
    state = rendering.state,
    actions = rendering.actions
  )
}