package com.rikin.hydrohomie.features.hydration.workflow.surface

import com.rikin.hydrohomie.app.mavericks.suface.NavTarget
import com.rikin.hydrohomie.app.workflow.domain.AppTransition
import com.rikin.hydrohomie.features.hydration.common.surface.Hydration
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationRendering
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.compose.composeViewFactory

@WorkflowUiExperimentalApi
val HydrationBinding = composeViewFactory<HydrationRendering> { rendering, _ ->
  Hydration(
    state = rendering.state,
    actions = rendering.actions,
    navigation = { location ->
      when (location) {
        NavTarget.HydrationTarget -> {}
        NavTarget.SettingsTarget -> {
          rendering.transitions(AppTransition.ToSettings)
        }

        NavTarget.StreaksTarget -> {
          rendering.transitions(AppTransition.ToStreaks)
        }

        NavTarget.OnboardingTarget -> TODO()
      }
    }
  )
}