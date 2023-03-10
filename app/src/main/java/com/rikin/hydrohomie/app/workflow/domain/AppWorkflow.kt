package com.rikin.hydrohomie.app.workflow.domain

import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.app.common.domain.toWeekday
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.HydrationMachine
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.InitialLoad
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.SettingsMachine
import com.rikin.hydrohomie.app.workflow.domain.WorkflowState.StreaksMachine
import com.rikin.hydrohomie.app.workflow.workers.InitialLoadWorker
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationOutput
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationOutput.SettingsTapped
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationOutput.StreaksTapped
import com.rikin.hydrohomie.features.hydration.workflow.domain.HydrationWorkflow
import com.rikin.hydrohomie.features.onboarding.surface.OnboardingStep
import com.rikin.hydrohomie.features.settings.common.domain.NotificationStatus
import com.rikin.hydrohomie.features.settings.workflow.domain.SettingsOutput
import com.rikin.hydrohomie.features.settings.workflow.domain.SettingsWorkflow
import com.rikin.hydrohomie.features.streak.workflow.domain.StreakWorkflow
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.renderChild
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.BackButtonScreen
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.backstack.BackStackScreen
import com.squareup.workflow1.ui.backstack.toBackStackScreen

sealed class WorkflowState(val appState: AppState) {
  class InitialLoad(appState: AppState) : WorkflowState(appState)
  class HydrationMachine(appState: AppState) : WorkflowState(appState)
  class StreaksMachine(appState: AppState) : WorkflowState(appState)
  class SettingsMachine(appState: AppState) : WorkflowState(appState)
}

@WorkflowUiExperimentalApi
class AppWorkflow(
  private val environment: AppEnvironment,
) : StatefulWorkflow<Unit, WorkflowState, Nothing, BackStackScreen<Any>>() {

  private val initialLoadWorker = InitialLoadWorker(
    environment.dates,
    environment.drinkRepository
  )

  override fun initialState(props: Unit, snapshot: Snapshot?): WorkflowState {
    return InitialLoad(
      appState = AppState(
        weekday = environment.dates.dayOfWeek.toWeekday(),
        defaultDrinkAmount = 16,
        notificationStatus = NotificationStatus.Disabled,
        onboardingStep = OnboardingStep.Finished,
        hydrations = emptyList()
      )
    )
  }

  private fun initialLoadAction(loadedState: AppState) = action {
    state = HydrationMachine(loadedState)
  }

  private fun onBack() = action {
    state = HydrationMachine(state.appState)
  }

  private fun onHydrationOutput(output: HydrationOutput) = action {
    state = when (output) {
      StreaksTapped -> {
        StreaksMachine(state.appState)
      }

      SettingsTapped -> {
        SettingsMachine(state.appState)
      }

      is HydrationOutput.UpdateState -> {
        val appState = with(state.appState) {
          copy(
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                output.state
              } else {
                hydrations[index]
              }
            }
          )
        }
        HydrationMachine(appState)
      }
    }
  }

  private fun onSettingsOutput(output: SettingsOutput) = action {
    state = when (output) {
      is SettingsOutput.UpdateState -> {
        val appState = with(state.appState) {
          copy(
            defaultDrinkAmount = output.state.defaultDrinkSize,
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                hydrations[index].copy(
                  goal = output.state.personalGoal,
                  drinkAmount = output.state.defaultDrinkSize
                )
              } else {
                hydrations[index]
              }
            }
          )
        }
        SettingsMachine(appState)
      }
    }
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
      is InitialLoad -> {
        context.runningWorker(initialLoadWorker) {
          initialLoadAction(it)
        }
      }

      is HydrationMachine -> {}
      is SettingsMachine -> {
        val settingsScreen = context.renderChild(
          child = SettingsWorkflow,
          props = renderState.appState
        ) {
          onSettingsOutput(it)
        }
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