package com.rikin.hydrohomie.features.hydration.common.surface

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
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
import com.rikin.hydrohomie.design.OceanBlue
import com.rikin.hydrohomie.design.SpaceCadetDark
import com.rikin.hydrohomie.design.SuperButton
import com.rikin.hydrohomie.design.ThemeThree
import com.rikin.hydrohomie.design.ThemeTwo
import com.rikin.hydrohomie.design.WaterGradient
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.features.settings.common.surface.pi
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.sin

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

@Composable
fun NewWaterContainer(state: HydrationState) {
  val infiniteTransition = rememberInfiniteTransition()
  val wave by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 4000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )
  val percent by animateFloatAsState(
    targetValue = state.percent * 1000f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioNoBouncy,
      stiffness = Spring.StiffnessVeryLow
    )
  )
  val amplitude by animateDpAsState(
    targetValue = when (state.percent) {
      0f -> 0.dp
      1f -> 0.dp
      else -> 8.dp
    },
    animationSpec = tween(
      durationMillis = 1000, easing = EaseInOut
    )
  )

  Box(modifier = Modifier
    .fillMaxSize()
    .drawBehind {
      val wavelength = size.width
      val stretch = 2.pi() / wavelength
      val xShift = wave * 2.pi()
      val yShift = size.height - size.height * (percent / 1000f)

      val segmentLength = wavelength / 30f
      val numSegments = (size.width / segmentLength).roundToInt()

      fun computeY(x: Float): Float {
        return amplitude.toPx() * sin(stretch * x - xShift) + yShift
      }

      var pointX = 0f
      val path = Path().apply {
        for (segment in 0..numSegments) {
          val pointY = computeY(pointX)
          when (segment) {
            0 -> moveTo(pointX, pointY)
            else -> lineTo(pointX, pointY)
          }
          pointX += segmentLength
        }
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        close()
      }

      drawPath(
        path = path,
        brush = WaterGradient,
      )
    }
  )
}

@Preview
@Composable
fun ActionBarPreview() {
  HydroHomieTheme {
    Box {
      ActionBar(
        state = HydrationState(drank = 0, goal = 64),
        actions = {},
        navigation = {}
      )
    }
  }
}

@Preview
@Composable
fun WaterContainerPreview() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = SpaceCadetDark),
    contentAlignment = Alignment.Center
  ) {
    NewWaterContainer(state = HydrationState(drank = 8, goal = 64))
  }
}

@Preview
@Composable
fun SpringAnimationTest() {
  var toggle by remember {
    mutableStateOf(false)
  }
  val percent by animateFloatAsState(
    targetValue = if (toggle) 0.5f else 0f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioHighBouncy,
      stiffness = Spring.StiffnessVeryLow,
      visibilityThreshold = 0.001f
    )
  )
  val percentScaled by animateFloatAsState(
    targetValue = if (toggle) 500f else 0f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioHighBouncy,
      stiffness = Spring.StiffnessVeryLow
    )
  )

  val boxWidth = remember { 150.dp }
  val boxHeight = remember { 300.dp }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = SpaceCadetDark),
    verticalArrangement = Arrangement.Center
  ) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
      Box(
        modifier = Modifier
          .width(boxWidth)
          .height(boxHeight)
          .background(
            color = Color.LightGray,
            shape = RoundedCornerShape(16.dp)
          )
          .clip(RoundedCornerShape(16.dp))
          .clickable {
            toggle = !toggle
          }
          .drawBehind {
            val width = size.width
            val height = size.height * percent * -1
            drawRoundRect(
              color = OceanBlue,
              topLeft = Offset(0f, size.height),
              size = Size(width, height)
            )
          }
      )
      Box(
        modifier = Modifier
          .width(boxWidth)
          .height(boxHeight)
          .background(
            color = Color.LightGray,
            shape = RoundedCornerShape(16.dp)
          )
          .clip(RoundedCornerShape(16.dp))
          .clickable {
            toggle = !toggle
          }
          .drawBehind {
            val width = size.width
            val height = size.height * (percentScaled / 1000f) * -1
            drawRoundRect(
              color = OceanBlue,
              topLeft = Offset(0f, size.height),
              size = Size(width, height)
            )
          }
      )
    }
  }
}
