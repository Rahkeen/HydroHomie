package com.rikin.hydrohomie.features.hydration.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppState

sealed class HydrationOutput {
    object StreaksTapped : HydrationOutput()
    object SettingsTapped : HydrationOutput()
    data class UpdateState(val state: AppState) : HydrationOutput()
}