@file:OptIn(WorkflowUiExperimentalApi::class)

package com.rikin.hydrohomie.app.platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import com.bumble.appyx.core.integrationpoint.ActivityIntegrationPoint
import com.bumble.appyx.core.integrationpoint.IntegrationPoint
import com.rikin.hydrohomie.app.mavericks.suface.MavericksApp
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

  private lateinit var integrationPoint: ActivityIntegrationPoint

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    integrationPoint = ActivityIntegrationPoint(
      activity = this,
      savedInstanceState = savedInstanceState
    )

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      CompositionLocalProvider(LocalIntegrationPoint provides integrationPoint) {
        MavericksApp()
      }
    }
  }
}

val LocalIntegrationPoint = staticCompositionLocalOf<IntegrationPoint> {
  noDefault()
}

private fun noDefault(): Nothing {
  error("Integration Point not provided")
}