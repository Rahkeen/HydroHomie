@file:OptIn(WorkflowUiExperimentalApi::class)

package com.rikin.hydrohomie.app.mavericks.domain

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.app.common.domain.toWeekday
import com.rikin.hydrohomie.app.platform.HydroHomieApplication
import com.rikin.hydrohomie.drinks.LocalDrink
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.settings.LocalSettings
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
      val settingsRepository = viewModelContext.app<HydroHomieApplication>().settingsRepository
      val dates = viewModelContext.app<HydroHomieApplication>().dates
      val environment = AppEnvironment(drinkRepository, settingsRepository, dates)
      return AppViewModel(state, environment)
    }
  }

  init {
    viewModelScope.launch {
      val currentWeek = environment.dates.currentWeek()

      val dateToDrink = environment.drinkRepository.getDrinksForRange(
        startDate = currentWeek[0],
        endDate = currentWeek[6]
      )

      val settings = environment.settingsRepository.getSettings()

      setState {
        AppState(
          drinkAmount = settings.drinkSize,
          weekday = environment.dates.dayOfWeek.toWeekday(),
          hydrations = buildList {
            currentWeek.forEachIndexed { index, date ->
              val drink = dateToDrink[date]
              if (index == environment.dates.dayOfWeek && drink != null) {
                add(HydrationState(drank = drink.count, goal = settings.goal, drinkAmount = settings.drinkSize))
              } else if (drink != null) {
                add(HydrationState(drank = drink.count, goal = drink.goal))
              } else {
                add(HydrationState(drank = 0, goal = settings.goal))
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
        withState { state ->
          viewModelScope.launch {
            environment
              .drinkRepository
              .updateDrink(
                day = environment.dates.today,
                drink = LocalDrink(
                  date = environment.dates.today,
                  count = state.hydrationState.drank,
                  goal = state.hydrationState.goal
                )
              )
          }
        }
      }
      is AppAction.Reset -> {
        setState {
          copy(
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                hydrations[index].copy(drank = 0)
              } else {
                hydrations[index]
              }
            }
          )
        }
        withState { state ->
          viewModelScope.launch {
            environment
              .drinkRepository
              .updateDrink(
                day = environment.dates.today,
                drink = LocalDrink(
                  date = environment.dates.today,
                  count = state.hydrationState.drank,
                  goal = state.hydrationState.goal
                )
              )
          }
        }
      }
      is AppAction.UpdateDrinkSize -> {
        Log.d("Update Drink Size", "${action.drinkSize}")
        setState {
          copy(drinkAmount = action.drinkSize)
        }
        withState { state ->
          viewModelScope.launch {
            environment
              .settingsRepository
              .updateSettings(
                LocalSettings(
                  drinkSize = state.settingsState.defaultDrinkSize,
                  goal = state.settingsState.personalGoal,
                )
              )
          }
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
        withState { state ->
          viewModelScope.launch {
            environment
              .settingsRepository
              .updateSettings(
                LocalSettings(
                  drinkSize = state.settingsState.defaultDrinkSize,
                  goal = state.settingsState.personalGoal,
                )
              )
          }
        }
      }
    }
  }
}
