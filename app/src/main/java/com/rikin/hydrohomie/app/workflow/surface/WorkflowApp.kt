@file:OptIn(WorkflowUiExperimentalApi::class)

package com.rikin.hydrohomie.app.workflow.surface

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rikin.hydrohomie.app.platform.HydroHomieApplication
import com.rikin.hydrohomie.app.workflow.domain.AppWorkflow
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.features.hydration.workflow.surface.HydrationBinding
import com.rikin.hydrohomie.features.settings.workflow.surface.SettingsBinding
import com.rikin.hydrohomie.features.streak.workflow.surface.StreakBinding
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewRegistry
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.backstack.BackStackContainer
import com.squareup.workflow1.ui.compose.WorkflowRendering
import com.squareup.workflow1.ui.compose.renderAsState

private val viewRegistry = ViewRegistry(
  BackStackContainer,
  HydrationBinding,
  StreakBinding,
  SettingsBinding
)

private val viewEnvironment = ViewEnvironment(mapOf(ViewRegistry to viewRegistry))

@Composable
fun WorkflowApp() {
  val systemUiController = rememberSystemUiController()

  SideEffect {
    systemUiController.setSystemBarsColor(
      color = Color.Transparent,
    )
  }

  HydroHomieTheme {
    val appWorkflow = getAppWorkflow(LocalContext.current.applicationContext)
    val rendering by appWorkflow.renderAsState(props = Unit, onOutput = {})
    WorkflowRendering(
      rendering = rendering,
      viewEnvironment = viewEnvironment
    )
  }
}

private fun getAppWorkflow(appContext: Context): AppWorkflow {
  return (appContext as HydroHomieApplication).appWorkflow
}