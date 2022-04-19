package com.rikin.hydrohomie.features.hydration.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.workflow.domain.AppTransition
import com.rikin.hydrohomie.features.hydration.mavericks.domain.HydrationState

data class HydrationRendering(
  val state: HydrationState,
  val actions: (AppAction) -> Unit,
  val transitions: (AppTransition) -> Unit
)
