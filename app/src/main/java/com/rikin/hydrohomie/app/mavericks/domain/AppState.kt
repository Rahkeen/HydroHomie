package com.rikin.hydrohomie.app.mavericks.domain

import com.airbnb.mvrx.MavericksState
import com.rikin.hydrohomie.app.mavericks.domain.Weekday.Monday
import com.rikin.hydrohomie.features.hydration.mavericks.domain.HydrationState
import com.rikin.hydrohomie.features.settings.mavericks.domain.SettingsState
import com.rikin.hydrohomie.features.streak.mavericks.domain.StreakState

data class AppState(
  val weekday: Weekday = Monday,
  val drinkAmount: Double = 8.0,
  val hydrations: List<HydrationState> = buildList {
    repeat(HYDRATION_LIMIT) {
      add(HydrationState())
    }
  }
) : MavericksState {
  val currentHydration = hydrations[weekday.ordinal]
  val streakState = StreakState(
    currentWeek = hydrations,
    currentDay = weekday
  )
  val settingsState = SettingsState(
    drinkAmount = drinkAmount,
    personalGoal = currentHydration.goal
  )
}

sealed class AppAction {
  object Drink : AppAction()
  object Reset : AppAction()
  data class UpdateGoal(val goal: Double) : AppAction()
  data class UpdateDrinkSize(val drinkSize: Double) : AppAction()
}

enum class Weekday {
  Monday,
  Tuesday,
  Wednesday,
  Thursday,
  Friday,
  Saturday,
  Sunday
}

fun Int.toWeekday(): Weekday {
  if (this > 6 || this < 0) throw IllegalStateException("Nah")
  return Weekday.values()[this]
}

const val HYDRATION_LIMIT = 7
