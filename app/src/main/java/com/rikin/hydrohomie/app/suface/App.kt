package com.rikin.hydrohomie.app.suface

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rikin.hydrohomie.app.domain.AppViewModel
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.features.hydration.surface.Hydration
import com.rikin.hydrohomie.features.streak.surface.Streaks

@Composable
fun App() {
  val systemUiController = rememberSystemUiController()
  val useDarkIcons = MaterialTheme.colors.isLight

  SideEffect {
    systemUiController.setSystemBarsColor(
      color = Color.Transparent,
      darkIcons = useDarkIcons
    )
  }

  HydroHomieTheme {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "hydration") {
      composable("hydration") {
        val viewModel: AppViewModel = mavericksActivityViewModel()
        val state by viewModel.collectAsState { it.currentHydration }
        Hydration(state = state, actions = viewModel::send, navigation = navController::navigate)
      }

      composable("streaks") {
        val viewModel: AppViewModel = mavericksActivityViewModel()
        val state by viewModel.collectAsState { it.streakState }
        Streaks(state = state)
      }
    }
  }
}
