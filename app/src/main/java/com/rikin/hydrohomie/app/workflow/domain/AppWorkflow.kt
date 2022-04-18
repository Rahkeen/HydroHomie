package com.rikin.hydrohomie.app.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.Hydrating
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationWorkflow
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.renderChild

sealed class WorkflowState(val appState: AppState) {
  class Hydrating(appState: AppState): WorkflowState(appState)
  class Streaks(appState: AppState): WorkflowState(appState)
  class Settings(appState: AppState): WorkflowState(appState)
}

object AppWorkflow: StatefulWorkflow<Unit, WorkflowState, Nothing, Any>() {

  override fun initialState(props: Unit, snapshot: Snapshot?): WorkflowState {
    return Hydrating(appState = AppState())
  }

  override fun render(
    renderProps: Unit,
    renderState: WorkflowState,
    context: RenderContext
  ): Any {
    return when(renderState) {
      is Hydrating -> {
        context.renderChild(child = HydrationWorkflow, props = renderState.appState)
      }
      else -> {  }
    }
  }

  override fun snapshotState(state: WorkflowState): Snapshot? = null
}