package com.rikin.hydrohomie.features.streak.common.surface

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.design.ButtonWidth
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.IconSize
import com.rikin.hydrohomie.design.JuicyOrange4
import com.rikin.hydrohomie.design.PopRed
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.SpaceCadetDark
import com.rikin.hydrohomie.design.ThemeOne
import com.rikin.hydrohomie.design.WaterGradient
import com.rikin.hydrohomie.design.WispyWhite
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StreakCup(hydrationState: HydrationState, dayLetter: String, isToday: Boolean = false) {

  val emoji = when {
    hydrationState.drank == 0 -> "😵"
    hydrationState.percent >= 1 -> "😎"
    else -> ""
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    val indicatorTransition = rememberInfiniteTransition()
    val indicatorY = indicatorTransition.animateFloat(
      initialValue = 0F,
      targetValue = 15F,
      animationSpec = infiniteRepeatable(
        animation = tween(durationMillis = 500, easing = LinearEasing),
        repeatMode = RepeatMode.Reverse
      )
    )
    if (isToday) {
      Icon(
        modifier = Modifier
          .size(IconSize)
          .graphicsLayer { translationY = indicatorY.value },
        imageVector = Icons.Rounded.ArrowDropDown,
        tint = ThemeOne,
        contentDescription = ""
      )
    } else {
      Spacer(modifier = Modifier.size(IconSize))
    }
    Box(
      modifier = Modifier
        .width(30.dp)
        .height(60.dp)
        .background(
          color = SpaceCadet,
          shape = MaterialTheme.shapes.small
        ),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(hydrationState.percent)
          .background(
            brush = WaterGradient,
            shape = MaterialTheme.shapes.small
          )
          .align(Alignment.BottomCenter)
      )
      Text(text = emoji, style = MaterialTheme.typography.caption)
    }
    Text(text = dayLetter, style = MaterialTheme.typography.caption, color = WispyWhite)
  }
}

@Preview(showBackground = true)
@Composable
fun StreakCupPreview() {
  HydroHomieTheme {
    Box(modifier = Modifier.background(SpaceCadetDark)) {
      StreakCup(
        hydrationState = HydrationState(),
        dayLetter = "M",
        isToday = true
      )
    }
  }
}

@Composable
fun WithIcon(
  icon: ImageVector,
  iconTint: Color,
  content: @Composable () -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(ElementPadding, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      modifier = Modifier.size(20.dp),
      imageVector = icon,
      tint = iconTint,
      contentDescription = ""
    )
    content()
  }
}

@Composable
fun WithIcon(
  painter: Painter,
  iconTint: Color,
  content: @Composable () -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(ElementPadding, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box(
      modifier = Modifier
        .size(28.dp)
        .background(
          shape = RoundedCornerShape(8.dp),
          color = iconTint.copy(alpha = 0.2f)
        ),
      contentAlignment = Alignment.Center
    ) {
      Image(
        modifier = Modifier.size(20.dp),
        painter = painter,
        colorFilter = ColorFilter.tint(iconTint),
        contentDescription = ""
      )
    }
    content()
  }
}

@Preview(showBackground = true)
@Composable
fun WithIconPreview() {
  HydroHomieTheme {
    Surface {
      WithIcon(painter = painterResource(id = R.drawable.ic_arrow_trending_up), iconTint = PopRed) {
        Text(
          text = "Streak",
          modifier = Modifier.wrapContentSize(),
          style = MaterialTheme.typography.body2
        )
      }
    }
  }
}

@Composable
fun ShareButtonGradientExample() {
  val interactionSource = remember { MutableInteractionSource() }
  val pressed by interactionSource.collectIsPressedAsState()

  val scale by animateFloatAsState(
    targetValue = if (pressed) 1.2f else 1f,
    animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
  )
  val alpha by animateFloatAsState(
    targetValue = if (pressed) 0.75f else 0f,
    animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
  )
  val blur by animateDpAsState(
    targetValue = if (pressed) 48.dp else 8.dp,
    animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
  )

  Box(
    modifier = Modifier
      .clickable(interactionSource = interactionSource, indication = null) { },
    contentAlignment = Alignment.Center
  ) {

    val brush = WaterGradient
    val size = 80.dp

    Box(
      modifier = Modifier
        .graphicsLayer(scaleX = scale, scaleY = scale)
        .size(size)
        .blur(radius = blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
        .drawBehind {
          drawRoundRect(
            brush = brush,
            cornerRadius = CornerRadius(16.dp.toPx()),
            alpha = alpha
          )
        }
    )
    Box(
      modifier = Modifier
        .size(size)
        .background(
          color = SpaceCadet,
          shape = RoundedCornerShape(16.dp)
        ),
      contentAlignment = Alignment.Center
    ) {
      Image(
        modifier = Modifier.size(30.dp),
        painter = painterResource(id = R.drawable.ic_share),
        contentDescription = "Share",
        colorFilter = ColorFilter.tint(WispyWhite)
      )
    }
  }
}

@Preview
@Composable
fun ShareButtonPreview() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = SpaceCadetDark),
    contentAlignment = Alignment.Center
  ) {
    ShareButtonGradientExample()
  }
}

@Preview
@Composable
fun ShareButton() {
  val scope = rememberCoroutineScope()

  val textPosition = remember {
    Animatable(100f)
  }

  val textAlpha = remember {
    Animatable(0f)
  }

  val color = JuicyOrange4

  Column(
    modifier = Modifier
      .clickable(interactionSource = MutableInteractionSource(), indication = null) {
        scope.launch {
          launch {
            textPosition.animateTo(
              targetValue = 0f,
              animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
              )
            )
          }
          launch {
            delay(80)
            textAlpha.animateTo(
              targetValue = 1f,
              animationSpec = tween()
            )
          }
          delay(2000)
          launch {
            textAlpha.animateTo(
              targetValue = 0f,
              animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
            )
            textPosition.snapTo(100f)
          }
        }
      },
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      modifier = Modifier.graphicsLayer(
        translationY = textPosition.value,
        alpha = textAlpha.value
      ).padding(4.dp),
      text = "Coming soon 😅",
      style = MaterialTheme.typography.caption
    )
    Box(
      modifier = Modifier
        .size(ButtonWidth)
        .background(
          color = color.copy(alpha = 0.3f),
          shape = RoundedCornerShape(16.dp)
        ),
      contentAlignment = Alignment.Center
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_share),
        colorFilter = ColorFilter.tint(color),
        contentDescription = "Share"
      )
    }
  }
}
