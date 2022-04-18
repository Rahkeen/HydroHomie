package com.rikin.hydrohomie.app.workflow.surface

import com.rikin.hydrohomie.app.workflow.domain.AppRendering
import com.rikin.hydrohomie.features.hydration.mavericks.surface.Hydration
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.compose.composeViewFactory

@WorkflowUiExperimentalApi
val AppBinding = composeViewFactory<AppRendering> { rendering, _ ->
  Hydration(
    state = rendering.state.currentHydration,
    actions = rendering.actions,
    navigation = {})
}