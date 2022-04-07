package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.google.firebase.firestore.FirebaseFirestore
import com.rikin.hydrohomie.app.platform.HydroHomieApplication
import com.rikin.hydrohomie.dates.Dates
import com.rikin.hydrohomie.features.hydration.domain.HydrationState

class AppViewModel(
  initialState: AppState,
  private val environment: AppEnvironment
) : MavericksViewModel<AppState>(initialState) {

  companion object : MavericksViewModelFactory<AppViewModel, AppState> {
    override fun create(viewModelContext: ViewModelContext, state: AppState): AppViewModel? {
      val store = viewModelContext.app<HydroHomieApplication>().store
      val dates = viewModelContext.app<HydroHomieApplication>().dates
      val environment = AppEnvironment(store, dates)
      return AppViewModel(state, environment)
    }
  }

  init {
    environment.firestore
      .collection("drinks")
      .document(environment.dates.today)
      .get()
      .addOnSuccessListener { document ->
        setState {
          AppState(
            dayOfWeek = environment.dates.dayOfWeek,
            hydrationWeek = buildList {
              repeat(7) { index ->
                if (index == environment.dates.dayOfWeekIndex) {
                  add(
                    HydrationState(
                      count = ((document.data?.get("count") ?: 0.0) as Double).toFloat(),
                    )
                  )
                } else if (index < environment.dates.dayOfWeekIndex) {
                  add(HydrationState(count = 8F))
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
          environment.firestore.collection("drinks").document(environment.dates.today).set(
            hashMapOf<String, Any>(
              "date" to environment.dates.today,
              "count" to state.hydrationWeek[state.dayOfWeek - 1].count + 1F,
            )
          ).addOnSuccessListener {
            setState {
              copy(
                hydrationWeek = List(hydrationWeek.size) { index ->
                  if (index == dayOfWeek - 1) {
                    hydrationWeek[index].copy(count = hydrationWeek[index].count + 1)
                  } else {
                    hydrationWeek[index]
                  }
                }
              )
            }
          }
        }
      }
      AppAction.Reset -> {
        environment.firestore.collection("drinks").document(environment.dates.today).delete()
          .addOnSuccessListener {
            setState {
              copy(
                hydrationWeek = List(hydrationWeek.size) { index ->
                  if (index == dayOfWeek - 1) {
                    HydrationState()
                  } else {
                    hydrationWeek[index]
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
