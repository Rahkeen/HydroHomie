package com.rikin.hydrohomie.app.suface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.rikin.hydrohomie.app.domain.AppViewModel
import com.rikin.hydrohomie.features.hydration.surface.Hydration
import com.rikin.hydrohomie.features.streak.surface.Streaks
import logcat.logcat

@Composable
fun App() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = "hydration") {
    composable("hydration") {
      val viewModel: AppViewModel = mavericksActivityViewModel()
      val state by viewModel.collectAsState()
      logcat { "Current State: $state" }
      Hydration(state = state.currentHydration, actions = viewModel::send, navigation = navController::navigate)
    }

    composable("streaks") {
      val viewModel: AppViewModel = mavericksActivityViewModel()
      val state by viewModel.collectAsState()
      logcat { "Current State: $state" }
      Streaks(state = state.hydrationWeek)
    }
  }
}
