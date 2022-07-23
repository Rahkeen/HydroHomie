package com.rikin.hydrohomie.features.settings.common.surface

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.design.HydroHomieTheme
import logcat.logcat
import kotlin.math.roundToInt

@Composable
fun SquigglySlider(
  modifier: Modifier,
  value: Float,
  low: Float = 0F,
  high: Float = 1F,
  color: Color = MaterialTheme.colors.primary,
  animateWave: Boolean = true,
  onValueChanged: (Float) -> Unit,
) {

  val waveHeight by animateFloatAsState(
    targetValue = if (animateWave) 1 / 8f else 0f,
    animationSpec = tween(500)
  )

  val animatedValue by animateFloatAsState(
    targetValue = value,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioNoBouncy,
      stiffness = Spring.StiffnessHigh
    )
  )


  val animTransition = rememberInfiniteTransition()
  val anim = animTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = InfiniteRepeatableSpec(
      animation = tween(1000, easing = LinearEasing),
    )
  )

  var width = remember { 1 }
  Box(
    modifier = modifier
      .onSizeChanged {
        width = it.width
      }
      .pointerInput(Unit) {
        detectDragGestures { change, _ ->

          val position = (change.position.x) / width // position on a scale from 0 to 1
          val settingsValue = (position * high).coerceIn(low, high)

          logcat {
            """
            Slider: $position
            Settings: $settingsValue
          """.trimIndent()
          }
          onValueChanged(settingsValue)
        }
      }
      .height(35.dp),
    contentAlignment = Alignment.Center
  ) {
    val path by remember { mutableStateOf(Path()) }
    Canvas(
      modifier = Modifier
        .clip(
          FractionClip(fraction = animatedValue, start = true)
        )
        .height(30.dp)
        .fillMaxWidth(),
      onDraw = {
        path.reset()
        path.moveTo(0f, size.height / 2)

        val inc = 12f
        var stepper = anim.value * (inc * 4) - (inc * 4)
        for (i in 1..(size.width / (inc * 4)).roundToInt() + 1) {

          path.quadraticBezierTo(
            x1 = stepper + (inc * 1),
            y1 = (size.height / 2) - (size.height * waveHeight),
            x2 = stepper + (inc * 2),
            y2 = size.height / 2,
          )

          path.quadraticBezierTo(
            x1 = stepper + (inc * 3),
            y1 = (size.height / 2) + (size.height * waveHeight),
            x2 = stepper + (inc * 4),
            y2 = size.height / 2,
          )

          stepper += (inc * 4)
        }

        drawPath(path = path, color = color, style = Stroke(12f))
      }
    )

    Box(
      modifier = Modifier
        .clip(
          FractionClip(fraction = animatedValue, start = false)
        )
        .fillMaxWidth()
        .height(2.dp)
        .background(
          color = MaterialTheme.colors.onSurface.copy(alpha = .2f),
          shape = RoundedCornerShape(2.dp)
        )
    )

    Box(
      modifier = Modifier
        .align(
          BiasAlignment(
            horizontalBias = (animatedValue * 2) - 1f,
            verticalBias = 0f
          )
        )
        .size(30.dp)
        .background(color = color, shape = CircleShape)
    )
  }

}

class FractionClip(val fraction: Float, val start: Boolean) : Shape {
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ): Outline {
    return Outline.Rectangle(
      rect = Rect(
        left = when (start) {
          true -> 0f
          false -> size.width * fraction
        },
        top = 0f,
        right = when (start) {
          true -> size.width * fraction
          false -> size.width
        },
        bottom = size.height,
      )
    )
  }
}

@Preview
@Composable
fun SquigglySliderPreview() {
  HydroHomieTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
      contentAlignment = Alignment.Center
    ) {
      var sliderValue by remember { mutableStateOf(0.5F) }
      SquigglySlider(
        modifier = Modifier.wrapContentSize(),
        value = sliderValue,
        low = 0F,
        high = 64F,
        onValueChanged = { sliderValue = it / 64F }
      )
    }
  }
}