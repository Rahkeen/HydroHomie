package com.rikin.hydrohomie.features.hydration.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action

object HydrationWorkflow: StatefulWorkflow<AppState, AppState, Nothing, HydrationRendering>() {
  override fun initialState(props: AppState, snapshot: Snapshot?): AppState {
    return props
  }

  private fun onAction(action: AppAction) = action {
    when(action) {
      AppAction.Drink -> {
        state = state.copy(
          hydrations = List(state.hydrations.size) { index ->
            if (index == state.weekday.ordinal) {
              state.hydrations[index].copy(
                drank = (state.hydrations[index].drank + state.drinkAmount).coerceAtMost(state.currentHydration.goal)
              )
            } else {
              state.hydrations[index]
            }
          }
        )
      }
      AppAction.Reset -> {
        state = state.copy(
          hydrations = List(state.hydrations.size) { index ->
            if (index == state.weekday.ordinal) {
              state.hydrations[index].copy(drank = 0.0)
            } else {
              state.hydrations[index]
            }
          }
        )
      }
      is AppAction.UpdateDrinkSize -> {}
      is AppAction.UpdateGoal -> {}
    }
  }

  override fun render(
    renderProps: AppState,
    renderState: AppState,
    context: RenderContext
  ): HydrationRendering {
    return HydrationRendering(
      state = renderState,
      actions = { context.actionSink.send(onAction(it)) }
    )
  }

  override fun snapshotState(state: AppState): Snapshot? {
    return null
  }
}