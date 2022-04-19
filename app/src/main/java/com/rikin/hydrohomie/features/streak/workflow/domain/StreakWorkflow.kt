package com.rikin.hydrohomie.features.streak.workflow.domain

import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.features.streak.common.domain.StreakState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow

object StreakWorkflow : StatefulWorkflow<AppState, StreakState, Nothing, StreakRendering>() {
  override fun initialState(props: AppState, snapshot: Snapshot?): StreakState {
    return props.streakState
  }

  override fun render(
    renderProps: AppState,
    renderState: StreakState,
    context: RenderContext
  ): StreakRendering {
    return StreakRendering(state = renderState)
  }

  override fun snapshotState(state: StreakState): Snapshot? {
    return null
  }
}