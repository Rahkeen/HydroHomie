package com.rikin.hydrohomie.features.hydration.surface

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
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.app.domain.AppAction
import com.rikin.hydrohomie.design.BlueSkiesEnd
import com.rikin.hydrohomie.design.CoolBlue
import com.rikin.hydrohomie.design.GangstaGreen
import com.rikin.hydrohomie.design.GangstaGreenDark
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.HydroIconButton
import com.rikin.hydrohomie.design.OzoneOrange
import com.rikin.hydrohomie.design.OzoneOrangeDark
import com.rikin.hydrohomie.design.PlayaPurple
import com.rikin.hydrohomie.design.PlayaPurpleDark
import com.rikin.hydrohomie.design.RadRed
import com.rikin.hydrohomie.design.RadRedDark
import com.rikin.hydrohomie.features.hydration.domain.HydrationState

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

    Column(
      modifier = Modifier
        .wrapContentSize()
        .padding(end = 16.dp)
        .background(
          color = MaterialTheme.colors.surface.copy(alpha = 0.5f),
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
        backgroundColor = PlayaPurple,
        iconTint = PlayaPurpleDark,
        icon = Icons.Rounded.Menu,
        iconDescription = "Clear",
        action = {
          navigation("streaks")
        }
      )

      HydroIconButton(
        backgroundColor = GangstaGreen,
        iconTint = GangstaGreenDark,
        icon = Icons.Rounded.MoreVert,
        iconDescription = "Settings",
        action = {
          navigation("settings")
        }
      )
    }
  }
}


@Composable
fun WaterContainer(state: HydrationState) {
  Box(modifier = Modifier.fillMaxSize()) {
    val fillPercent by animateFloatAsState(
      targetValue = (state.drank / state.goal).toFloat(),
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
    ) { currentState ->
      when {
        currentState.drank > 0 && currentState.drank < currentState.goal -> 16.dp
        else -> 0.dp
      }
    }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(fraction = fillPercent)
        .background(
          shape = RoundedCornerShape(
            topStart = waterCornerRadius,
            topEnd = waterCornerRadius
          ),
          brush = Brush.verticalGradient(
            colors = listOf(
              CoolBlue,
              BlueSkiesEnd,
            )
          )
        )
        .align(Alignment.BottomCenter)
    )
  }
}

@Preview
@Composable
fun HydrationPreview() {
  HydroHomieTheme {
    Hydration(
      state = HydrationState(drank = 4.0),
      actions = {},
      navigation = {}
    )
  }
}
