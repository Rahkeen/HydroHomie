package com.rikin.hydrohomie.app.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState

data class AppRendering(
  val state: AppState,
  val actions: (AppAction) -> Unit
)
