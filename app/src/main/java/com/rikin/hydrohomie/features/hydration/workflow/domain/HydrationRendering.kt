package com.rikin.hydrohomie.features.hydration.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.rikin.hydrohomie.app.workflow.domain.AppTransition

data class HydrationRendering(
  val state: AppState,
  val actions: (AppAction) -> Unit,
  val transitions: (AppTransition) -> Unit
)
