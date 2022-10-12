package com.rikin.hydrohomie.features.hydration.common.surface

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.JuicyOrange1
import com.rikin.hydrohomie.design.JuicyOrange2
import com.rikin.hydrohomie.design.JuicyOrange3
import com.rikin.hydrohomie.design.JuicyOrange6
import com.rikin.hydrohomie.design.JuicyOrange7
import com.rikin.hydrohomie.design.JuicyOrange8
import com.rikin.hydrohomie.design.JuicyOrange9
import com.rikin.hydrohomie.design.NeonPink
import com.rikin.hydrohomie.design.WispyWhite
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import kotlin.math.roundToInt

@Preview
@Composable
fun IndicatorLine(modifier: Modifier = Modifier) {
  Box(modifier = modifier) {
    Canvas(
      modifier = Modifier
        .width(60.dp)
        .fillMaxHeight()
    ) {
      val horizontalPadding = 0F
      val verticalPadding = 64.dp.toPx()
      val lineStrokeWidth = 4.dp.toPx()
      val pathEffect = null
      val colorsList = listOf(
        JuicyOrange1,
        JuicyOrange2,
        JuicyOrange3,
        JuicyOrange6,
        JuicyOrange7,
        JuicyOrange8,
        JuicyOrange9
      )
      val rulerBrush = Brush.verticalGradient(
        colors = colorsList
      )

      // ruler lines
      drawLine(
        color = colorsList[0],
        start = Offset(horizontalPadding, size.height * 0.125F),
        end = Offset(horizontalPadding + 20.dp.toPx(), size.height * 0.125F),
        strokeWidth = lineStrokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
      )

      drawLine(
        color = colorsList[1],
        start = Offset(horizontalPadding, size.height * 0.25F),
        end = Offset(horizontalPadding + 20.dp.toPx(), size.height * 0.25F),
        strokeWidth = lineStrokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
      )

      drawLine(
        color = colorsList[2],
        start = Offset(horizontalPadding, size.height * 0.375F),
        end = Offset(horizontalPadding + 20.dp.toPx(), size.height * 0.375F),
        strokeWidth = lineStrokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
      )

      drawLine(
        color = colorsList[3],
        start = Offset(horizontalPadding, size.height * 0.5F),
        end = Offset(horizontalPadding + 40.dp.toPx(), size.height * 0.5F),
        strokeWidth = lineStrokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
      )

      drawLine(
        color = colorsList[4],
        start = Offset(horizontalPadding, size.height * 0.625F),
        end = Offset(horizontalPadding + 20.dp.toPx(), size.height * 0.625F),
        strokeWidth = lineStrokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
      )

      drawLine(
        color = colorsList[5],
        start = Offset(horizontalPadding, size.height * 0.75F),
        end = Offset(horizontalPadding + 20.dp.toPx(), size.height * 0.75F),
        strokeWidth = lineStrokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
      )

      drawLine(
        color = colorsList[6],
        start = Offset(horizontalPadding, size.height * 0.875F),
        end = Offset(horizontalPadding + 20.dp.toPx(), size.height * 0.875F),
        strokeWidth = lineStrokeWidth,
        cap = StrokeCap.Round,
        pathEffect = pathEffect
      )
    }
  }
}

@Composable
fun BoxScope.IndicatorLineAlternate(state: HydrationState) {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .wrapContentWidth(),
    verticalArrangement = Arrangement.SpaceEvenly
  ) {

    val portion = state.goal / 2
    for (i in 1 downTo 1) {
      val amount = portion * i
      val on = amount <= state.drank
      val big = i == 1
      IndicatorTick(on, big, amount)
    }
  }
}

@Preview
@Composable
fun IndicatorLineAlternatePreview() {
  HydroHomieTheme {
    Box(modifier = Modifier.fillMaxSize()) {
      IndicatorLineAlternate(state = HydrationState(drank = 32))
    }
  }
}

@Composable
fun IndicatorTick(on: Boolean = false, big: Boolean = false, value: Int = 0) {
  val width = if (big) 40.dp else 20.dp
  val transition = updateTransition(targetState = on, label = "IndicatorTick")
  val shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
  val lineSize = 2.dp

  val filledWidth by transition.animateDp(
    transitionSpec = { tween(durationMillis = 500) },
    label = "IndicatorTickWidth"
  ) { toggle ->
    when (toggle) {
      true -> width
      false -> 0.dp
    }
  }


  val textColor by transition.animateColor(
    transitionSpec = { tween(easing = LinearEasing) },
    label = "IndicatorTickColor"
  ) { toggle ->
    when (toggle) {
      true -> WispyWhite
      false -> Color.Gray
    }
  }

  Row(
    modifier = Modifier.wrapContentSize(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(ElementPadding)
  ) {
    Box(
      modifier = Modifier
        .requiredWidth(width)
        .wrapContentHeight()
        .clip(shape)
    ) {

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(lineSize)
          .background(
            color = Color.Gray,
            shape = shape
          )
      )

      Box(
        modifier = Modifier
          .width(filledWidth)
          .height(lineSize)
          .background(
            color = NeonPink,
            shape = shape
          )
      )
    }

    Text(text = "$value oz", fontSize = 14.sp, color = textColor)
  }
}

@Preview
@Composable
fun IndicatorTickPreview() {
  var toggle by remember { mutableStateOf(false) }
  Box(modifier = Modifier
    .wrapContentSize()
    .clickable { toggle = !toggle })
  IndicatorTick(on = toggle)
}
