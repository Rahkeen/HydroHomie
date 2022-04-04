package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.google.firebase.firestore.FirebaseFirestore
import com.rikin.hydrohomie.app.platform.HydroHomieApplication
import com.rikin.hydrohomie.features.hydration.domain.HydrationState
import logcat.logcat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AppViewModel(
  initialState: AppState,
  private val store: FirebaseFirestore
) : MavericksViewModel<AppState>(initialState) {

  private val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
  private val date = LocalDate.now().format(formatter)
  private val day = LocalDate.now().dayOfWeek

  companion object : MavericksViewModelFactory<AppViewModel, AppState> {
    override fun create(viewModelContext: ViewModelContext, state: AppState): AppViewModel? {
      val store = viewModelContext.app<HydroHomieApplication>().store
      return AppViewModel(state, store)
    }
  }

  init {
    store
      .collection("drinks")
      .document(date)
      .get()
      .addOnSuccessListener { document ->
        setState {
          AppState(
            dayOfWeek = day.value,
            hydrationWeek = buildList {
              repeat(7) { index ->
                if (index == day.value - 1) {
                  add(HydrationState(
                    count = ((document.data?.get("count") ?: 0.0) as Double).toFloat(),
                  ))
                } else if (index < day.value) {
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
          store.collection("drinks").document(date).set(
            hashMapOf<String, Any>(
              "date" to date,
              "count" to state.hydrationWeek[state.dayOfWeek-1].count + 1F,
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
        store.collection("drinks").document(date).delete().addOnSuccessListener {
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
