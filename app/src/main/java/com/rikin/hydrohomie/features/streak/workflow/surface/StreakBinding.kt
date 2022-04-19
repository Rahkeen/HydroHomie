package com.rikin.hydrohomie.features.streak.workflow.surface

import com.rikin.hydrohomie.features.streak.mavericks.surface.Streaks
import com.rikin.hydrohomie.features.streak.workflow.domain.StreakRendering
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.compose.composeViewFactory

@WorkflowUiExperimentalApi
val StreakBinding = composeViewFactory<StreakRendering> { rendering, _ ->
    Streaks(rendering.state.streakState)
}
