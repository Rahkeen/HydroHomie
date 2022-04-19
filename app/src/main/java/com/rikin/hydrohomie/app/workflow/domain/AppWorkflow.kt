package com.rikin.hydrohomie.app.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.HydrationMachine
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.SettingsMachine
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.StreaksMachine
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationOutput
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationOutput.SettingsTapped
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationOutput.StreaksTapped
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationWorkflow
import com.rikin.hydrohomie.features.settings.workflow.domain.SettingsWorkflow
import com.rikin.hydrohomie.features.streak.workflow.domain.StreakWorkflow
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.renderChild
import com.squareup.workflow1.ui.BackButtonScreen
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.backstack.BackStackScreen
import com.squareup.workflow1.ui.backstack.toBackStackScreen

sealed class WorkflowState(val appState: AppState) {
    class HydrationMachine(appState: AppState) : WorkflowState(appState)
    class StreaksMachine(appState: AppState) : WorkflowState(appState)
    class SettingsMachine(appState: AppState) : WorkflowState(appState)
}

@WorkflowUiExperimentalApi
object AppWorkflow : StatefulWorkflow<Unit, WorkflowState, Nothing, BackStackScreen<Any>>() {

    override fun initialState(props: Unit, snapshot: Snapshot?): WorkflowState {
        return HydrationMachine(appState = AppState())
    }

    private fun onHydrationOutput(output: HydrationOutput) = action {
        state = when (output) {
            StreaksTapped -> {
                StreaksMachine(state.appState)
            }
            SettingsTapped -> {
                SettingsMachine(state.appState)
            }
        }
    }

    private fun onBack() = action {
        state = HydrationMachine(state.appState)
    }

    override fun render(
        renderProps: Unit,
        renderState: WorkflowState,
        context: RenderContext
    ): BackStackScreen<Any> {
        val backstack = mutableListOf<Any>()

        val hydrationScreen = context.renderChild(
            child = HydrationWorkflow,
            props = renderState.appState
        ) {
            onHydrationOutput(it)
        }
        backstack.add(hydrationScreen)

        when (renderState) {
            is HydrationMachine -> {}
            is SettingsMachine -> {
                val settingsScreen = context.renderChild(
                    child = SettingsWorkflow,
                    props = renderState.appState
                )
                backstack.add(
                    BackButtonScreen(settingsScreen) {
                        context.actionSink.send(onBack())
                    }
                )
            }
            is StreaksMachine -> {
                val streaksScreen = context.renderChild(
                    child = StreakWorkflow,
                    props = renderState.appState
                )
                backstack.add(
                    BackButtonScreen(streaksScreen) {
                        context.actionSink.send(onBack())
                    }
                )
            }
        }

        return backstack.toBackStackScreen()
    }

    override fun snapshotState(state: WorkflowState): Snapshot? = null
}