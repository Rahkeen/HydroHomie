package com.rikin.hydrohomie.features.settings.mavericks.surface

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.rikin.hydrohomie.app.mavericks.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.domain.AppEnvironment
import com.rikin.hydrohomie.app.mavericks.domain.AppState
import com.rikin.hydrohomie.app.mavericks.domain.AppViewModel
import com.rikin.hydrohomie.dates.FakeDates
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.PlayaPurple
import com.rikin.hydrohomie.design.RadRed
import com.rikin.hydrohomie.design.Typography
import com.rikin.hydrohomie.design.imageGradient
import com.rikin.hydrohomie.drinks.FakeDrinkRepository
import com.rikin.hydrohomie.features.settings.mavericks.domain.SettingsState
import logcat.logcat
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun Settings(state: SettingsState, actions: (AppAction) -> Unit) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {

    Box(
      modifier = Modifier
        .width(100.dp)
        .height(100.dp)
        .align(Alignment.CenterHorizontally)
        .background(brush = imageGradient, shape = RoundedCornerShape(16.dp)),
      contentAlignment = Alignment.Center
    ) {
      Text("💩")
    }

    Text(
      text = "@heyrikin",
      modifier = Modifier.align(Alignment.CenterHorizontally),
      style = MaterialTheme.typography.body1,
    )

    GoalSlider(
      low = 30.0,
      high = 200.0,
      current = state.personalGoal,
      sliderName = "Goal",
      onUpdate = { actions(AppAction.UpdateGoal(goal = round(it))) }
    )

    GoalSlider(
      low = 4.0,
      high = 32.0,
      current = state.drinkAmount,
      sliderName = "Drink Size",
      color = RadRed,
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

@Composable
fun GoalSlider(
  low: Double,
  high: Double,
  current: Double,
  sliderName: String,
  color: Color = PlayaPurple,
  onUpdate: (Double) -> Unit
) {
  val draggableState = rememberDraggableState { delta ->
    logcat(tag = "GoalSlider") { "$delta" }
    val smoothDelta = when {
      delta > 0 -> 1
      delta < 0 -> -1
      else -> 0
    }

    val update = current + smoothDelta
    onUpdate(update.coerceIn(low..high))
  }
  val progress by animateFloatAsState(targetValue = (current / high).toFloat())

  Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Text(
      modifier = Modifier.padding(start = 8.dp),
      text = sliderName,
      style = Typography.caption
    )
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      Arrangement.spacedBy(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .weight(5F)
          .height(60.dp),
      ) {
        Box(
          modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = progress)
            .draggable(
              state = draggableState,
              orientation = Orientation.Horizontal
            )
            .background(
              color = color,
              shape = RoundedCornerShape(16.dp)
            )
        )
      }

      Text(
        modifier = Modifier.weight(1F),
        text = "${current.roundToInt()} oz",
        style = Typography.caption,
        textAlign = TextAlign.Start
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GoalSliderPreview() {
  HydroHomieTheme {
    GoalSlider(
      low = 30.0,
      high = 200.0,
      current = 60.0,
      sliderName = "Goal",
      onUpdate = {}
    )
  }
}
