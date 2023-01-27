package com.rikin.hydrohomie.design

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        shape = MaterialTheme.shapes.medium,
        color = backgroundColor
      )
      .clip(MaterialTheme.shapes.medium)
      .clickable { action() },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(IconSize),
      imageVector = icon,
      contentDescription = iconDescription,
      tint = iconTint
    )
  }
}

@Composable
fun AnimatedDeleteButton(
  backgroundColor: Color,
  iconTint: Color,
  action: () -> Unit,
) {
  val lidPathData = PathData {
    moveTo(18.0f, 4.0f)
    horizontalLineToRelative(-2.5f)
    lineToRelative(-0.71f, -0.71f)
    curveToRelative(-0.18f, -0.18f, -0.44f, -0.29f, -0.7f, -0.29f)
    horizontalLineTo(9.91f)
    curveToRelative(-0.26f, 0.0f, -0.52f, 0.11f, -0.7f, 0.29f)
    lineTo(8.5f, 4.0f)
    horizontalLineTo(6.0f)
    curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
    reflectiveCurveToRelative(0.45f, 1.0f, 1.0f, 1.0f)
    horizontalLineToRelative(12.0f)
    curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
    reflectiveCurveToRelative(-0.45f, -1.0f, -1.0f, -1.0f)
    close()
  }

  val canPathData = PathData {
    // base of trash can
    moveTo(6.0f, 19.0f)
    curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
    horizontalLineToRelative(8.0f)
    curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
    verticalLineTo(9.0f)
    curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
    horizontalLineTo(8.0f)
    curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
    verticalLineToRelative(10.0f)
    close()
  }

  val lidTranslationY = remember {
    Animatable(0f)
  }

  val canRotation = remember {
    Animatable(0f)
  }

  val canTranslationY = remember {
    Animatable(0f)
  }

  val scope = rememberCoroutineScope()

  val iconVector = rememberVectorPainter(
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
    autoMirror = true,
  ) { viewportWidth, viewportHeight ->
    Group(
      name = "LidVector",
      translationY = lidTranslationY.value
    ) {
      Group(name = "Lid") {
        Path(
          pathData = lidPathData,
          fill = SolidColor(PopRed),
          fillAlpha = 1f,
          stroke = null,
          strokeAlpha = 1f,
          strokeLineCap = StrokeCap.Butt,
          strokeLineJoin = StrokeJoin.Bevel,
          strokeLineMiter = 1f,
          pathFillType = DefaultFillType
        )
      }
    }

    Group(
      name = "CanVector",
      pivotX = viewportWidth / 2,
      pivotY = viewportHeight / 2,
      translationY = canTranslationY.value,
      rotation = canRotation.value
    ) {
      Group(name = "Can") {
        Path(
          pathData = canPathData,
          fill = SolidColor(PopRed),
          fillAlpha = 1f,
          stroke = null,
          strokeAlpha = 1f,
          strokeLineCap = StrokeCap.Butt,
          strokeLineJoin = StrokeJoin.Bevel,
          strokeLineMiter = 1f,
          pathFillType = DefaultFillType
        )
      }
    }
  }
  Box(
    modifier = Modifier
      .width(ButtonWidth)
      .height(ButtonHeight)
      .background(
        shape = MaterialTheme.shapes.medium,
        color = backgroundColor
      )
      .clip(MaterialTheme.shapes.medium)
      .clickable {
        action()
        scope.launch {
          lidTranslationY.animateTo(
            targetValue = -2f,
            animationSpec = spring(
              dampingRatio = Spring.DampingRatioNoBouncy,
              stiffness = Spring.StiffnessLow
            )
          )
          lidTranslationY.animateTo(
            targetValue = 0F,
            animationSpec = spring(
              dampingRatio = Spring.DampingRatioHighBouncy,
              stiffness = Spring.StiffnessLow
            )
          )
        }
        scope.launch {
          delay(700)
          canRotation.animateTo(0f, animationSpec = keyframes {
            durationMillis = 800
            -10f at 200
            10f at 400
            -10f at 600
          })
        }
        scope.launch {
          delay(700)
          canTranslationY.animateTo(
            targetValue = 2f,
            animationSpec = spring(
              dampingRatio = Spring.DampingRatioLowBouncy,
              stiffness = Spring.StiffnessLow
            )
          )
          canTranslationY.animateTo(
            targetValue = 0F,
            animationSpec = spring(
              dampingRatio = Spring.DampingRatioNoBouncy,
              stiffness = Spring.StiffnessLow
            )
          )
        }
      },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(IconSize),
      painter = iconVector,
      contentDescription = "Delete",
      tint = iconTint
    )
  }
}

@Composable
fun SuperButton(action: () -> Unit) {
  val inifiniteTransition = rememberInfiniteTransition()
  val pinky by inifiniteTransition.animateColor(
    initialValue = ThemeFour,
    targetValue = ThemeFive,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 5000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )
  val purply by inifiniteTransition.animateColor(
    initialValue = ThemeFive,
    targetValue = ThemeFour,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 7500, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  Box(
    modifier = Modifier
      .width(ButtonWidth)
      .height(ButtonHeight)
      .background(
        shape = MaterialTheme.shapes.medium,
        brush = Brush.verticalGradient(
          colors = listOf(
            pinky,
            purply
          )
        )
      )
      .clip(MaterialTheme.shapes.medium)
      .clickable { action() },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(IconSize),
      imageVector = Icons.Rounded.Add,
      contentDescription = "Drink",
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
    SuperButton({})
  }
}