package com.rikin.hydrohomie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rikin.hydrohomie.app.domain.AppViewModel
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.features.hydration.ui.Hydration
import com.rikin.hydrohomie.features.streak.ui.Streaks

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val systemUiController = rememberSystemUiController()
      val useDarkIcons = MaterialTheme.colors.isLight

      SideEffect {
        systemUiController.setSystemBarsColor(
          color = Color.Transparent,
          darkIcons = useDarkIcons
        )
      }

      HydroHomieTheme {
        App()
      }
    }
  }
}

@Composable
fun App() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = "hydration") {
    composable("hydration") {
      val viewModel: AppViewModel = mavericksViewModel()
      val state by viewModel.collectAsState { it.currentHydration }
      Hydration(state = state, actions = viewModel::send, navigation = navController::navigate)
    }

    composable("streaks") {
      val viewModel: AppViewModel = mavericksViewModel()
      val state by viewModel.collectAsState { it.streaks }
      Streaks(state = state)
    }
  }
}