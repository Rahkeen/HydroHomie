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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.PlayaPurple
import com.rikin.hydrohomie.design.Typography
import com.rikin.hydrohomie.design.imageGradient
import com.rikin.hydrohomie.features.settings.domain.SettingsState
import kotlin.math.roundToInt

@Composable
fun Settings(state: SettingsState) {
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
    GoalSlider(low = 30F, high = 200F, current = 40F, sliderName = "Goal")
  }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
  HydroHomieTheme {
    Settings(SettingsState())
  }
}

@Composable
fun GoalSlider(
  low: Float,
  high: Float,
  current: Float,
  sliderName: String,
) {
  var currentState by remember { mutableStateOf(current.roundToInt()) }
  val draggableState = rememberDraggableState() { delta ->
    val reducedDelta = delta / 5F
    currentState = (currentState + reducedDelta).coerceIn(low..high).roundToInt()
  }
  val progress = currentState / high
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
            .fillMaxWidth(fraction = progress)
            .draggable(state = draggableState, orientation = Orientation.Horizontal)
            .background(
              color = PlayaPurple,
              shape = RoundedCornerShape(16.dp)
            )
        )
      }

      Text(text = "$currentState oz", style = Typography.caption)
    }
  }
}

@Preview
@Composable
fun GoalSliderPreview() {
  HydroHomieTheme {
    GoalSlider(
      low = 30F,
      high = 200F,
      current = 60F,
      sliderName = "Goal"
    )
  }
}
