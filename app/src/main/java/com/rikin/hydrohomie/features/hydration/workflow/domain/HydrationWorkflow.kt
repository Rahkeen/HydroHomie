package com.rikin.hydrohomie.features.hydration.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.rikin.hydrohomie.app.workflow.domain.AppTransition
import com.rikin.hydrohomie.features.hydration.mavericks.domain.HydrationState
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationOutput.UpdateState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action

object HydrationWorkflow :
  StatefulWorkflow<AppState, HydrationState, HydrationOutput, HydrationRendering>() {
  override fun initialState(props: AppState, snapshot: Snapshot?): HydrationState {
    return props.currentHydration
  }

  override fun onPropsChanged(
    old: AppState,
    new: AppState,
    state: HydrationState
  ): HydrationState {
    return new.currentHydration
  }

  private fun onAction(action: AppAction) = action {
    when (action) {
      AppAction.Drink -> {
        state = state.copy(
          drank = state.drank + state.drinkAmount
        )
        setOutput(UpdateState(state))
      }
      AppAction.Reset -> {
        state = state.copy(drank = 0.0)
        setOutput(UpdateState(state))
      }
      is AppAction.UpdateDrinkSize -> {}
      is AppAction.UpdateGoal -> {}
    }
  }

  private fun onTransition(transition: AppTransition) = action {
    when (transition) {
      AppTransition.ToStreak -> {
        setOutput(HydrationOutput.StreaksTapped)
      }
      AppTransition.ToSetting -> {
        setOutput(HydrationOutput.SettingsTapped)
      }
      AppTransition.ToHydration -> {}
    }
  }

  override fun render(
    renderProps: AppState,
    renderState: HydrationState,
    context: RenderContext
  ): HydrationRendering {
    return HydrationRendering(
      state = renderState,
      actions = { context.actionSink.send(onAction(it)) },
      transitions = { context.actionSink.send(onTransition(it)) }
    )
  }

  override fun snapshotState(state: HydrationState): Snapshot? {
    return null
  }
}