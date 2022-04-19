package com.rikin.hydrohomie.features.settings.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action

object SettingsWorkflow : StatefulWorkflow<AppState, AppState, Nothing, SettingsRendering>() {
    override fun initialState(props: AppState, snapshot: Snapshot?): AppState {
        return props
    }

    private fun onAction(action: AppAction) = action {
        when (action) {
            AppAction.Drink -> Unit
            AppAction.Reset -> Unit
            is AppAction.UpdateDrinkSize -> {
                state = state.copy(drinkAmount = action.drinkSize)
            }
            is AppAction.UpdateGoal -> {
                state = state.copy(
                    hydrations = List(state.hydrations.size) { index ->
                        if (index == state.weekday.ordinal) {
                            state.hydrations[index].copy(goal = action.goal)
                        } else {
                            state.hydrations[index]
                        }
                    }
                )
            }
        }
    }

    override fun render(
        renderProps: AppState,
        renderState: AppState,
        context: RenderContext
    ): SettingsRendering {
        return SettingsRendering(
            state = renderState,
            actions = { context.actionSink.send(onAction(it)) }
        )
    }

    override fun snapshotState(state: AppState): Snapshot? {
        return null
    }
}