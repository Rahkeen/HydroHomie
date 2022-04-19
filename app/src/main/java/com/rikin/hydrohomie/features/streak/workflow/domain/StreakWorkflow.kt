package com.rikin.hydrohomie.features.streak.workflow.domain

import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow

object StreakWorkflow: StatefulWorkflow<AppState, AppState, Nothing, StreakRendering>() {
    override fun initialState(props: AppState, snapshot: Snapshot?): AppState {
        return props
    }

    override fun render(
        renderProps: AppState,
        renderState: AppState,
        context: RenderContext
    ): StreakRendering {
        return StreakRendering(state = renderState)
    }

    override fun snapshotState(state: AppState): Snapshot? {
        return null
    }

}