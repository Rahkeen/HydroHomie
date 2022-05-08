package com.rikin.hydrohomie.design

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HydroIconButton(
  backgroundColor: Color,
  iconTint: Color,
  icon: ImageVector,
  iconDescription: String,
  action: () -> Unit,
) {
  Box(
    modifier = Modifier
      .width(ButtonWidth)
      .height(ButtonHeight)
      .background(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
      )
      .clip(RoundedCornerShape(16.dp))
      .clickable { action() },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(20.dp),
      imageVector = icon,
      contentDescription = iconDescription,
      tint = iconTint
    )
  }
}

@Composable
fun SuperButton(action: () -> Unit, ) {
  val inifiniteTransition = rememberInfiniteTransition()
  val pinky by inifiniteTransition.animateColor(
    initialValue = NeonPink,
    targetValue = NeonLighterBlue,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 5000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )
  val purply by inifiniteTransition.animateColor(
    initialValue = NeonDarkerPurple,
    targetValue = NeonDarkBlue,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 10000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  Box(
    modifier = Modifier
      .width(ButtonWidth)
      .height(ButtonHeight)
      .background(
        shape = RoundedCornerShape(16.dp),
        brush = Brush.verticalGradient(
          colors = listOf(
            pinky,
            purply
          )
        )
      )
      .clip(RoundedCornerShape(16.dp))
      .clickable { action() },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(20.dp),
      imageVector = Icons.Rounded.Add,
      contentDescription = "",
      tint = Color.White
    )
  }
}

@Preview
@Composable
fun HydroIconButtonPreview() {
  HydroHomieTheme {
    HydroIconButton(
      backgroundColor = NeonPurple,
      iconTint = NeonPink,
      icon = Icons.Rounded.Add,
      iconDescription = "Add",
      action = {}
    )
  }
}

@Preview
@Composable
fun SuperButtonPreview() {
  HydroHomieTheme {
    SuperButton({} )
  }
}

val ButtonWidth = 60.dp
val ButtonHeight = 60.dp