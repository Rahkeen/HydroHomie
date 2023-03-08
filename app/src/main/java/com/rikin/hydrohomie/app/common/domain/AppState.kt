package com.rikin.hydrohomie.app.common.domain

import com.airbnb.mvrx.MavericksState
import com.rikin.hydrohomie.app.common.domain.Weekday.Monday
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.features.settings.common.domain.SettingsState
import com.rikin.hydrohomie.features.streak.common.domain.StreakState

data class AppState(
  val weekday: Weekday = Monday,
  val defaultDrinkAmount: Int = 8,
  val notificationsEnabled: Boolean = false,
  val hydrations: List<HydrationState> = buildList {
    repeat(HYDRATION_LIMIT) {
      add(
        HydrationState(
          drank = 0,
          goal = 64,
          drinkAmount = defaultDrinkAmount
        )
      )
    }
  },
) : MavericksState {

  val hydrationState = hydrations[weekday.ordinal]
  val streakState = StreakState(
    currentWeek = hydrations,
    currentDay = weekday
  )
  val settingsState = SettingsState(
    personalGoal = hydrationState.goal,
    defaultDrinkSize = defaultDrinkAmount,
    notificationsEnabled = notificationsEnabled
  )
}


sealed class AppAction {
  object Drink : AppAction()
  object Reset : AppAction()
  data class UpdateGoal(val goal: Int) : AppAction()
  data class UpdateDrinkSize(val drinkSize: Int) : AppAction()
  data class UpdateNotifications(val enabled: Boolean): AppAction()
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
