package com.rikin.hydrohomie.features.hydration.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.app.domain.AppAction
import com.rikin.hydrohomie.app.domain.AppState
import com.rikin.hydrohomie.design.BlueSkiesEnd
import com.rikin.hydrohomie.design.BlueSkiesStart
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.HydroIconButton
import com.rikin.hydrohomie.design.OzoneOrange
import com.rikin.hydrohomie.design.OzoneOrangeDark
import com.rikin.hydrohomie.design.RadRed
import com.rikin.hydrohomie.design.RadRedDark

@Composable
fun Hydration(state: AppState, actions: (AppAction) -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background)
  ) {
    val fillPercent by animateFloatAsState(
      targetValue = state.count / state.goal,
      animationSpec = tween(
        durationMillis = 300,
        easing = LinearEasing
      )
    )

    val cornerTransition = updateTransition(
      targetState = state,
      label = "CornerTransition"
    )

    val waterCornerRadius by cornerTransition.animateDp(
      transitionSpec = {
        tween(
          durationMillis = 300,
          easing = LinearEasing
        )
      },
      label = "WaterCornerRadius"
    ) { state ->
      when {
        state.count > 0 && state.count < state.goal -> 16.dp
        else -> 0.dp
      }
    }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(fraction = fillPercent)
        .background(
          shape = RoundedCornerShape(waterCornerRadius),
          brush = Brush.verticalGradient(
            colors = listOf(
              BlueSkiesStart,
              BlueSkiesEnd
            )
          )
        )
        .align(Alignment.BottomCenter)
    )

    Column(
      modifier = Modifier
        .wrapContentSize()
        .padding(end = 16.dp)
        .background(
          color = MaterialTheme.colors.surface.copy(alpha = 0.3F),
          shape = RoundedCornerShape(22.dp)
        )
        .padding(8.dp)
        .align(Alignment.CenterEnd),
      verticalArrangement = Arrangement.spacedBy(
        space = 8.dp,
        alignment = Alignment.CenterVertically
      ),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      HydroIconButton(
        backgroundColor = OzoneOrange,
        iconTint = OzoneOrangeDark,
        icon = Icons.Rounded.Add,
        iconDescription = "Add",
        action = {
          actions(
            AppAction.Drink
          )
        }
      )

      HydroIconButton(
        backgroundColor = RadRed,
        iconTint = RadRedDark,
        icon = Icons.Rounded.Refresh,
        iconDescription = "Clear",
        action = {
          actions(
            AppAction.Reset
          )
        }
      )
    }
  }
}

@Preview
@Composable
fun HydrationPreview() {
  HydroHomieTheme {
    Hydration(state = AppState(), actions = {})
  }
}
