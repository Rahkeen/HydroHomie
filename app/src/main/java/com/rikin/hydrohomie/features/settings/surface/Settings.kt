package com.rikin.hydrohomie.features.settings.surface

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.app.domain.AppAction
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.PlayaPurple
import com.rikin.hydrohomie.design.RadRed
import com.rikin.hydrohomie.design.Typography
import com.rikin.hydrohomie.design.imageGradient
import com.rikin.hydrohomie.features.settings.domain.SettingsState
import logcat.logcat
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun Settings(state: SettingsState, actions: (AppAction) -> Unit) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
  ) {

    // Profile Image Section
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

    // Username
    Text(
      text = "@heyrikin",
      modifier = Modifier.align(Alignment.CenterHorizontally),
      style = MaterialTheme.typography.body1,
    )

    // Set Your Goal
    GoalSlider(
      low = 30.0,
      high = 200.0,
      current = state.personalGoal,
      sliderName = "Goal",
      onUpdate = { actions(AppAction.UpdateGoal(goal = round(it))) }
    )

    // Set your Default Increment
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

@Composable
fun GoalSlider(
  low: Double,
  high: Double,
  current: Double,
  sliderName: String,
  color: Color = PlayaPurple,
  onUpdate: (Double) -> Unit
) {
  val draggableState = rememberDraggableState() { delta ->
    val maxStep = (high - low) / 50.0
    val reducedDelta = delta.toDouble().coerceIn(-maxStep..maxStep)
    val updatedValue = (current + reducedDelta).coerceIn(low..high)
    logcat(tag = "GoalSlider") { "$updatedValue" }
    onUpdate(updatedValue)
  }
  val progress = current / high
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
          .weight(1F)
          .height(60.dp),
      ) {
        Box(
          modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = progress.toFloat())
            .draggable(state = draggableState, orientation = Orientation.Horizontal)
            .background(
              color = color,
              shape = RoundedCornerShape(16.dp)
            )
        )
      }

      Text(text = "${current.roundToInt()} oz", style = Typography.caption)
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
