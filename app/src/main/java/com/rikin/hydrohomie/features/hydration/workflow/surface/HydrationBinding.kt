package com.rikin.hydrohomie.features.hydration.workflow.surface

import com.rikin.hydrohomie.features.hydration.mavericks.surface.Hydration
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationRendering
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.compose.composeViewFactory

@WorkflowUiExperimentalApi
val HydrationBinding =  composeViewFactory<HydrationRendering> { rendering, _ ->
  Hydration(
    state = rendering.state.currentHydration,
    actions = rendering.actions,
    navigation = {})
}