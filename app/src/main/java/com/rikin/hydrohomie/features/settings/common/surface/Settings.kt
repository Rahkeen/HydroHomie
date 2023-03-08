package com.rikin.hydrohomie.features.settings.common.surface

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.app.common.domain.AppEnvironment
import com.rikin.hydrohomie.app.common.domain.AppState
import com.rikin.hydrohomie.app.mavericks.domain.AppViewModel
import com.rikin.hydrohomie.dates.FakeDates
import com.rikin.hydrohomie.design.ComponentPadding
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.ImageGradient
import com.rikin.hydrohomie.design.PopYellow
import com.rikin.hydrohomie.design.ProfilePicSize
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.ThemeGamertag
import com.rikin.hydrohomie.design.ThemeSliderPrimary
import com.rikin.hydrohomie.drinks.FakeDrinkRepository
import com.rikin.hydrohomie.features.settings.common.domain.SettingsState
import com.rikin.hydrohomie.settings.FakeSettingsRepository
import kotlin.math.roundToInt

@Composable
fun Settings(state: SettingsState, actions: (AppAction) -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background)
      .padding(ComponentPadding),
    verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    // Profile Pic Placeholder
    Box(
      modifier = Modifier
        .width(ProfilePicSize)
        .height(ProfilePicSize)
        .background(brush = ImageGradient, shape = MaterialTheme.shapes.medium),
      contentAlignment = Alignment.Center
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "main icon"
      )
    }

    Text(
      text = "@hydrohomie",
      style = MaterialTheme.typography.body1,
      color = ThemeGamertag
    )

    NewGoalSlider(
      low = 0,
      high = 128,
      progress = state.personalGoal / 128f,
      label = "Goal",
      color = ThemeSliderPrimary,
      update = { percent ->
        val amount = (percent * 128).roundToInt()
        actions(AppAction.UpdateGoal(goal = amount))
      }
    )

    DrinkSizeSelectionGroup(
      drinks = state.drinkSizes,
      action = { actions(AppAction.UpdateDrinkSize(it.amount)) })

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = ElementPadding),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(text = "Notifications", style = MaterialTheme.typography.caption)
        Text(
          text = "~4 per day",
          fontSize = 12.sp,
          color = PopYellow
        )
      }
      Checkbox(
        checked = state.notificationsEnabled,
        onCheckedChange = { actions(AppAction.UpdateNotifications(it)) },
        colors = CheckboxDefaults.colors(
          checkedColor = PopYellow,
          uncheckedColor = PopYellow,
          checkmarkColor = SpaceCadet
        )
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
  HydroHomieTheme {
    Settings(state = SettingsState(
      personalGoal = 64,
      defaultDrinkSize = 16,
      notificationsEnabled = false
    ), actions = {})
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
          settingsRepository = FakeSettingsRepository(),
          dates = FakeDates(),
        )
      )
    }

    val state by viewModel.collectAsState { it.settingsState }
    Settings(state = state, actions = viewModel::send)
  }
}