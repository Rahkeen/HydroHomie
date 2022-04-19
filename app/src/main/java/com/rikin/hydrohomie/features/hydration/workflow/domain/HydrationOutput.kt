package com.rikin.hydrohomie.features.hydration.workflow.domain

import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState

sealed class HydrationOutput {
  object StreaksTapped : HydrationOutput()
  object SettingsTapped : HydrationOutput()
  data class UpdateState(val state: HydrationState) : HydrationOutput()
}