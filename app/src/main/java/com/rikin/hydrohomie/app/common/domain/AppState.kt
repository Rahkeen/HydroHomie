package com.rikin.hydrohomie.app.common.domain

import com.airbnb.mvrx.MavericksState
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.features.onboarding.surface.OnboardingState
import com.rikin.hydrohomie.features.onboarding.surface.OnboardingStep
import com.rikin.hydrohomie.features.settings.common.domain.NotificationStatus
import com.rikin.hydrohomie.features.settings.common.domain.SettingsState
import com.rikin.hydrohomie.features.streak.common.domain.StreakState

data class AppState(
  val weekday: Weekday = Weekday.Monday,
  val defaultDrinkAmount: Int = 16,
  val notificationStatus: NotificationStatus = NotificationStatus.Disabled,
  val onboardingStep: OnboardingStep = OnboardingStep.Welcome,
  val hydrations: List<HydrationState> = listOf(
    HydrationState(drank = 32, goal = 64),
    HydrationState(drank = 32, goal = 64),
    HydrationState(drank = 32, goal = 64),
    HydrationState(drank = 32, goal = 64),
    HydrationState(drank = 32, goal = 64),
    HydrationState(drank = 32, goal = 64),
    HydrationState(drank = 32, goal = 64),
  ),
) : MavericksState {

  val hydrationState = hydrations[weekday.ordinal]
  val streakState = StreakState(
    currentWeek = hydrations,
    currentDay = weekday
  )
  val settingsState = SettingsState(
    personalGoal = hydrationState.goal,
    defaultDrinkSize = defaultDrinkAmount,
    notificationStatus = notificationStatus
  )
  val onboardingState = OnboardingState(
    step = onboardingStep,
    settingsState = settingsState
  )
}


sealed class AppAction {
  object Drink : AppAction()
  object Reset : AppAction()
  data class UpdateGoal(val goal: Int) : AppAction()
  data class UpdateDrinkSize(val drinkSize: Int) : AppAction()
  data class UpdateNotifications(val status: NotificationStatus) : AppAction()
  object NextOnboardingStep : AppAction()
  object OnboardingFinished : AppAction()
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
