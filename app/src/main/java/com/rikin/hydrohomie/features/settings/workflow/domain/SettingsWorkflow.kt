package com.rikin.hydrohomie.features.settings.workflow.domain

import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.features.settings.common.domain.SettingsState
import com.rikin.hydrohomie.features.settings.workflow.domain.SettingsOutput.UpdateState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action

object SettingsWorkflow :
  StatefulWorkflow<AppState, SettingsState, SettingsOutput, SettingsRendering>() {
  override fun initialState(props: AppState, snapshot: Snapshot?): SettingsState {
    return props.settingsState
  }

  private fun onAction(action: AppAction) = action {
    when (action) {
      AppAction.Drink -> Unit
      AppAction.Reset -> Unit
      is AppAction.UpdateDrinkSize -> {
        state = state.copy(defaultDrinkSize = action.drinkSize)
        setOutput(UpdateState(state))
      }
      is AppAction.UpdateGoal -> {
        state = state.copy(personalGoal = action.goal)
        setOutput(UpdateState(state))
      }

      is AppAction.UpdateNotifications -> TODO()
    }
  }

  override fun render(
    renderProps: AppState,
    renderState: SettingsState,
    context: RenderContext
  ): SettingsRendering {
    return SettingsRendering(
      state = renderState,
      actions = { context.actionSink.send(onAction(it)) }
    )
  }

  override fun snapshotState(state: SettingsState): Snapshot? {
    return null
  }
}