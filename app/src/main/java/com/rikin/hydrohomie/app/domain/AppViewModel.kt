package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.rikin.hydrohomie.app.platform.HydroHomieApplication
import com.rikin.hydrohomie.drinkrepo.DrinkModel
import com.rikin.hydrohomie.features.hydration.domain.HydrationState
import kotlinx.coroutines.launch

class AppViewModel(
  initialState: AppState,
  private val environment: AppEnvironment
) : MavericksViewModel<AppState>(initialState) {

  companion object : MavericksViewModelFactory<AppViewModel, AppState> {
    override fun create(viewModelContext: ViewModelContext, state: AppState): AppViewModel {
      val store = viewModelContext.app<HydroHomieApplication>().store
      val dates = viewModelContext.app<HydroHomieApplication>().dates
      val environment = AppEnvironment(store, dates)
      return AppViewModel(state, environment)
    }
  }

  init {
    viewModelScope.launch {
      val drink = environment.store.getDrink(environment.dates.today)
      setState {
        AppState(
          weekday = environment.dates.dayOfWeek.toWeekday(),
          hydrations = buildList {
            repeat(HYDRATION_LIMIT) { index ->
              if (index == environment.dates.dayOfWeek) {
                add(HydrationState(count = drink.count))
              } else if (index < environment.dates.dayOfWeek) {
                add(HydrationState(count = 8.0))
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
    when (action) {
      AppAction.Drink -> {
        withState { state ->
          viewModelScope.launch {
            environment
              .store
              .updateDrink(
                day = environment.dates.today,
                drink = DrinkModel(state.currentHydration.count + 1)
              )
          }
        }
        setState {
          copy(
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                hydrations[index].copy(count = hydrations[index].count + 1)
              } else {
                hydrations[index]
              }
            }
          )
        }
      }
      AppAction.Reset -> {
        viewModelScope.launch {
          environment
            .store
            .updateCount(
              day = environment.dates.today,
              count = 0.0
            )
        }
        setState {
          copy(
            hydrations = List(hydrations.size) { index ->
              if (index == weekday.ordinal) {
                HydrationState()
              } else {
                hydrations[index]
              }
            }
          )
        }
      }
      AppAction.Streaks -> {}
    }
  }
}
