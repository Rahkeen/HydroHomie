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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.app.mavericks.suface.NavTarget
import com.rikin.hydrohomie.design.ComponentPadding
import com.rikin.hydrohomie.design.DrinkDisplay
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.IconDeleteButton
import com.rikin.hydrohomie.design.MediumCornerRadius
import com.rikin.hydrohomie.design.NavButton
import com.rikin.hydrohomie.design.SuperButton
import com.rikin.hydrohomie.design.ThemeThree
import com.rikin.hydrohomie.design.ThemeTwo
import com.rikin.hydrohomie.design.WaterGradient
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState

@Composable
fun BoxScope.ActionBar(
  state: HydrationState,
  actions: (AppAction) -> Unit,
  navigation: (NavTarget) -> Unit
) {
  Column(
    modifier = Modifier
      .wrapContentSize()
      .padding(end = ComponentPadding, bottom = ComponentPadding)
      .align(Alignment.BottomEnd),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(space = ElementPadding)
  ) {

    Column(
      modifier = Modifier
        .wrapContentSize(),
      verticalArrangement = Arrangement.spacedBy(
        space = 16.dp,
        alignment = Alignment.CenterVertically
      ),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      NavButton(
        iconTint = ThemeTwo,
        painter = painterResource(id = R.drawable.ic_calendar_days),
        iconDescription = "Streaks",
        action = {
          navigation(NavTarget.StreaksTarget)
        }
      )
      NavButton(
        iconTint = ThemeThree,
        painter = painterResource(id = R.drawable.ic_adjustments_horizontal),
        iconDescription = "Settings",
        action = {
          navigation(NavTarget.SettingsTarget)
        }
      )
      Spacer(modifier = Modifier.height(64.dp))
      DrinkDisplay(state = state)
      IconDeleteButton(action = { actions(AppAction.Reset) })
      SuperButton(state = state, action = { actions(AppAction.Drink) })
    }
  }
}

@Composable
fun WaterContainer(state: HydrationState) {
  Box(modifier = Modifier.fillMaxSize()) {
    val fillPercent by animateFloatAsState(
      targetValue = (state.drank.toFloat() / state.goal),
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
