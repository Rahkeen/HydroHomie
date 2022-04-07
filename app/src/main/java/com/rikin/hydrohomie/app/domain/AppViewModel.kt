package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.rikin.hydrohomie.app.platform.HydroHomieApplication
import com.rikin.hydrohomie.features.hydration.domain.HydrationState

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
    environment.store
      .collection("drinks")
      .document(environment.dates.today)
      .get()
      .addOnSuccessListener { document ->
        setState {
          AppState(
            weekday = environment.dates.dayOfWeek.toWeekday(),
            hydrations = buildList {
              repeat(7) { index ->
                if (index == environment.dates.dayOfWeek) {
                  add(
                    HydrationState(
                      count = (document.data?.get("count") ?: 0.0) as Double,
                    )
                  )
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
          environment.store.collection("drinks").document(environment.dates.today).set(
            hashMapOf<String, Any>(
              "date" to environment.dates.today,
              "count" to state.hydrations[state.weekday.ordinal].count + 1,
            )
          ).addOnSuccessListener {
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
        }
      }
      AppAction.Reset -> {
        environment.store
          .collection("drinks")
          .document(environment.dates.today)
          .update("count", 0)
          .addOnSuccessListener {
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
      }
      AppAction.Streaks -> {}
    }
  }
}
