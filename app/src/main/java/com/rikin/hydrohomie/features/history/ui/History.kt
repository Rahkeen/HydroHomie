package com.rikin.hydrohomie.features.history.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.app.domain.AppState
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.Porcelain
import com.rikin.hydrohomie.features.hydration.ui.WaterContainer

@Composable
fun History(states: List<AppState>) {
  var toggle by remember { mutableStateOf(false) }
  val transition = updateTransition(targetState = toggle, label = "toggle")
  val rotationY by transition.animateFloat(label = "") { on ->
    when {
      on -> -5f
      else -> 0f
    }
  }
  val translationX by transition.animateFloat(label = "") { on ->
    when {
      on -> 40f
      else -> 0f
    }
  }
  val translationY by transition.animateFloat(label = "") { on ->
    when {
      on -> -20f
      else -> 0f
    }
  }
  Box(
    modifier = Modifier.clickable { toggle = !toggle }
  ) {
    for (i in states.lastIndex downTo 0) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .shadow(elevation = 4.dp)
          .graphicsLayer(
            rotationY = rotationY,
            translationX = translationX * i,
            translationY = translationY * i
          ),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .background(color = Porcelain)
        ) {
          WaterContainer(state = states[i])
        }
      }
    }
  }
}

@Preview
@Composable
fun HistoryPreview() {
  HydroHomieTheme {
    History(
      states = listOf(
        AppState(count = 5F, goal = 8F),
        AppState(count = 7F, goal = 8F),
        AppState(count = 7F, goal = 8F),
        AppState(count = 8F, goal = 8F),
        AppState(count = 8F, goal = 8F),
        AppState(count = 8F, goal = 8F),
      )
    )
  }
}