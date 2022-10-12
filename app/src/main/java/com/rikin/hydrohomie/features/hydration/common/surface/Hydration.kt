package com.rikin.hydrohomie.features.hydration.common.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.mvrx.compose.collectAsState
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.app.mavericks.domain.AppViewModel
import com.rikin.hydrohomie.dates.FakeDates
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.drinks.FakeDrinkRepository
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.settings.FakeSettingsRepository

@Composable
fun Hydration(
  state: HydrationState,
  actions: (AppAction) -> Unit,
  navigation: (String) -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background)
  ) {
    WaterContainer(state = state)
    ActionBar(state = state, actions = actions, navigation = navigation)
  }
}

@Preview
@Composable
fun HydrationPreview() {
  HydroHomieTheme {
    Hydration(
      state = HydrationState(drank = 16),
      actions = {},
      navigation = {}
    )
  }
}

@Preview
@Composable
fun FunctionalHydrationPreview() {
  HydroHomieTheme {
    val viewModel = remember {
      AppViewModel(
        initialState = AppState(),
        environment = AppEnvironment(
          drinkRepository = FakeDrinkRepository(),
          settingsRepository = FakeSettingsRepository(),
          dates = FakeDates(),
        )
      )
    }

    val state by viewModel.collectAsState { it.hydrationState }
    Hydration(
      state = state,
      actions = viewModel::send,
      navigation = {}
    )
  }
}
