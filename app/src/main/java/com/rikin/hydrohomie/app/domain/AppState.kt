package com.rikin.hydrohomie.app.domain

import com.airbnb.mvrx.MavericksState
import com.rikin.hydrohomie.app.domain.Weekday.Monday
import com.rikin.hydrohomie.features.hydration.domain.HydrationState
import com.rikin.hydrohomie.features.streak.domain.StreakState

data class AppState(
  val weekday: Weekday = Monday,
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
}

enum class AppAction {
  Drink,
  Reset,
  Streaks
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
