package com.rikin.hydrohomie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rikin.hydrohomie.app.domain.AppAction
import com.rikin.hydrohomie.app.domain.AppState
import com.rikin.hydrohomie.app.domain.AppViewModel
import com.rikin.hydrohomie.design.CoolBlue
import com.rikin.hydrohomie.design.CrazyCream
import com.rikin.hydrohomie.design.FilledButton
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.OzoneOrange
import com.rikin.hydrohomie.design.RadRed

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val systemUiController = rememberSystemUiController()

      SideEffect {
        systemUiController.setSystemBarsColor(
          color = Color.Transparent
        )
      }

      HydroHomieTheme {
        // A surface container using the 'background' color from the theme
        val viewModel: AppViewModel = mavericksViewModel()
        val state by viewModel.collectAsState()
        Home(state = state, actions = viewModel::send)
      }
    }
  }
}


@Composable
fun Home(state: AppState, actions: (AppAction) -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = CrazyCream)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(fraction = state.count / state.goal)
        .background(color = CoolBlue)
        .align(Alignment.BottomCenter)
    )

    Column(
      modifier = Modifier
        .wrapContentSize()
        .align(Alignment.Center),
      verticalArrangement = Arrangement.spacedBy(
        space = 16.dp,
        alignment = Alignment.CenterVertically
      ),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "${state.count.toInt()} / ${state.goal.toInt()}",
        modifier = Modifier.wrapContentSize(),
        style = MaterialTheme.typography.h1
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(
          space = 8.dp,
          alignment = Alignment.CenterHorizontally
        )
      ) {
        FilledButton(
          backgroundColor = RadRed,
          textColor = Color.White,
          text = "💧",
          action = {
            actions(
              AppAction.Drink
            )
          }
        )

        FilledButton(
          backgroundColor = OzoneOrange,
          textColor = Color.White,
          text = "♻️",
          action = {
            actions(
              AppAction.Reset
            )
          }
        )
      }
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
  HydroHomieTheme {
    Home(state = AppState(count = 5F, goal = 8F), actions = {})
  }
}