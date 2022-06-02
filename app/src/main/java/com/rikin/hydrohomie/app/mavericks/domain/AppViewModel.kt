@file:OptIn(WorkflowUiExperimentalApi::class)

package com.rikin.hydrohomie.app.mavericks.domain

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.app.common.domain.HYDRATION_LIMIT
import com.rikin.hydrohomie.app.common.domain.toWeekday
import com.rikin.hydrohomie.app.platform.HydroHomieApplication
import com.rikin.hydrohomie.drinks.DrinkModel
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import kotlinx.coroutines.launch
import logcat.logcat

class AppViewModel(
  initialState: AppState,
  private val environment: AppEnvironment
) : MavericksViewModel<AppState>(initialState) {

  companion object : MavericksViewModelFactory<AppViewModel, AppState> {
    override fun create(viewModelContext: ViewModelContext, state: AppState): AppViewModel {
      val drinkRepository = viewModelContext.app<HydroHomieApplication>().drinkRepository
      val dates = viewModelContext.app<HydroHomieApplication>().dates
      val environment = AppEnvironment(drinkRepository, dates)
      return AppViewModel(state, environment)
    }
  }

  init {
    viewModelScope.launch {
      val drink = environment.drinkRepository.getDrink(environment.dates.today)
      setState {
        AppState(
          weekday = environment.dates.dayOfWeek.toWeekday(),
          hydrations = buildList {
            repeat(HYDRATION_LIMIT) { index ->
              if (index == environment.dates.dayOfWeek) {
                add(HydrationState(drank = drink.count, goal = drink.goal))
              } else if (index < environment.dates.dayOfWeek) {
                add(HydrationState(drank = 64.0))
              } else {
                add(HydrationState())
              }
            }
          }
        )
      }
    }
  }

  fun send(action: AppAction) {
    logcat { action.toString() }
    when (action) {
      is AppAction.Drink -> {
        withState { state ->
          viewModelScope.launch {
            environment
              .drinkRepository
              .updateDrink(
                day = environment.dates.today,
                drink = DrinkModel(
                  count = (state.hydrationState.drank + state.drinkAmount)
                    .coerceAtMost(state.hydrationState.goal),
                  goal = state.hydrationState.goal
                )
              )
          }
        }
        setState {
          copy(
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                hydrations[index].copy(
                  drank = (hydrations[index].drank + drinkAmount).coerceAtMost(hydrationState.goal)
                )
              } else {
                hydrations[index]
              }
            }
          )
        }
      }
      is AppAction.Reset -> {
        viewModelScope.launch {
          environment
            .drinkRepository
            .updateCount(
              day = environment.dates.today,
              count = 0.0
            )
        }
        setState {
          copy(
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                hydrations[index].copy(drank = 0.0)
              } else {
                hydrations[index]
              }
            }
          )
        }
      }
      is AppAction.UpdateDrinkSize -> {
        setState {
          copy(drinkAmount = action.drinkSize)
        }
      }
      is AppAction.UpdateGoal -> {
        setState {
          copy(
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                hydrations[index].copy(goal = action.goal)
              } else {
                hydrations[index]
              }
            }
          )
        }
      }
    }
  }
}
