package com.rikin.hydrohomie.features.settings.common.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.app.mavericks.domain.AppViewModel
import com.rikin.hydrohomie.dates.FakeDates
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.NeonLightBlue
import com.rikin.hydrohomie.design.NeonMagenta
import com.rikin.hydrohomie.design.ImageGradient
import com.rikin.hydrohomie.drinks.FakeDrinkRepository
import com.rikin.hydrohomie.features.settings.common.domain.SettingsState
import kotlin.math.round

@Composable
fun Settings(state: SettingsState, actions: (AppAction) -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background),
    verticalArrangement = Arrangement.spacedBy(ElementPadding, Alignment.CenterVertically),
  ) {

    // Profile Pic Placeholder
    Box(
      modifier = Modifier
        .width(ProfilePicSize)
        .height(ProfilePicSize)
        .align(Alignment.CenterHorizontally)
        .background(brush = ImageGradient, shape = MaterialTheme.shapes.medium),
      contentAlignment = Alignment.Center
    ) {
      Text("💩")
    }

    Text(
      text = "@heyrikin",
      modifier = Modifier.align(Alignment.CenterHorizontally),
      style = MaterialTheme.typography.body1,
      color = NeonLightBlue
    )

    GoalSlider(
      low = 32.0,
      high = 200.0,
      current = state.personalGoal,
      sliderName = "Goal",
      color = NeonMagenta,
      onUpdate = { actions(AppAction.UpdateGoal(goal = round(it))) }
    )

    GoalSlider(
      low = 4.0,
      high = 32.0,
      current = state.drinkAmount,
      sliderName = "Drink Size",
      color = NeonLightBlue,
      onUpdate = { actions(AppAction.UpdateDrinkSize(drinkSize = round(it))) }
    )
  }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
  HydroHomieTheme {
    Settings(state = SettingsState(), actions = {})
  }
}

@Preview(showBackground = true)
@Composable
fun FunctionalSettingsPreview() {
  HydroHomieTheme {
    val viewModel = remember {
      AppViewModel(
        initialState = AppState(),
        environment = AppEnvironment(
          drinkRepository = FakeDrinkRepository(),
          dates = FakeDates(),
        )
      )
    }

    val state by viewModel.collectAsState { it.settingsState }
    Settings(state = state, actions = viewModel::send)
  }
}

private val ProfilePicSize = 100.dp