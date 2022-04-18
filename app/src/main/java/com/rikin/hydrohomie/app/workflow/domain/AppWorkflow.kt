package com.rikin.hydrohomie.app.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action

object AppWorkflow: StatefulWorkflow<Unit, AppState, Nothing, AppRendering>() {

  override fun initialState(props: Unit, snapshot: Snapshot?): AppState {
    return AppState()
  }

  private fun onAction(action: AppAction) = action {
    when (action) {
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
    renderProps: Unit,
    renderState: AppState,
    context: RenderContext
  ): AppRendering {
    return AppRendering(
      state = renderState,
      actions = { action ->
        context.actionSink.send(onAction(action))
      }
    )
  }

  override fun snapshotState(state: AppState): Snapshot? = null
}