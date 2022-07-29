package com.rikin.hydrohomie.features.hydration.common.surface

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.design.ButtonWidth
import com.rikin.hydrohomie.design.ComponentPadding
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.HydroIconButton
import com.rikin.hydrohomie.design.MediumCornerRadius
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.SuperButton
import com.rikin.hydrohomie.design.ThemeOne
import com.rikin.hydrohomie.design.ThemeThree
import com.rikin.hydrohomie.design.ThemeTwo
import com.rikin.hydrohomie.design.WaterGradient
import com.rikin.hydrohomie.design.WispyWhite
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import kotlin.math.roundToInt

@Composable
fun BoxScope.ActionBar(
  state: HydrationState,
  actions: (AppAction) -> Unit,
  navigation: (String) -> Unit
) {
  Column(
    modifier = Modifier
      .wrapContentSize()
      .padding(end = ComponentPadding, bottom = ComponentPadding)
      .align(Alignment.BottomEnd),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(space = ElementPadding)
  ) {
    Box(
      modifier = Modifier
        .size(ButtonWidth + ElementPadding)
        .background(
          color = SpaceCadet,
          shape = CircleShape
        ),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "${state.drank.roundToInt()}",
        style = MaterialTheme.typography.body2,
        color = WispyWhite
      )
    }
    Column(
      modifier = Modifier
        .wrapContentSize()
        .background(
          color = SpaceCadet,
          shape = MaterialTheme.shapes.large
        )
        .padding(ElementPadding),
      verticalArrangement = Arrangement.spacedBy(
        space = ElementPadding,
        alignment = Alignment.CenterVertically
      ),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      HydroIconButton(
        backgroundColor = ThemeOne.copy(alpha = 0.2F),
        iconTint = ThemeOne,
        icon = Icons.Rounded.Refresh,
        iconDescription = "Clear",
        action = {
          actions(
            AppAction.Reset
          )
        }
      )
      HydroIconButton(
        backgroundColor = ThemeTwo.copy(alpha = 0.2F),
        iconTint = ThemeTwo,
        icon = Icons.Rounded.Menu,
        iconDescription = "Clear",
        action = {
          navigation("streaks")
        }
      )
      HydroIconButton(
        backgroundColor = ThemeThree.copy(0.2F),
        iconTint = ThemeThree,
        icon = Icons.Rounded.MoreVert,
        iconDescription = "Settings",
        action = {
          navigation("settings")
        }
      )
      SuperButton(action = { actions(AppAction.Drink) })
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
        currentState.drank > 0 && currentState.drank < currentState.goal -> MediumCornerRadius
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
          brush = WaterGradient
        )
        .align(Alignment.BottomCenter)
    )
  }
}

@Preview
@Composable
fun ActionBarPreview() {
  HydroHomieTheme {
    Box {
      ActionBar(
        state = HydrationState(),
        actions = {},
        navigation = {}
      )
    }
  }
}
