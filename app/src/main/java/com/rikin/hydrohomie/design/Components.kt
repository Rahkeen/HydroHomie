package com.rikin.hydrohomie.design

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
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
fun NavButton(
  iconTint: Color,
  painter: Painter,
  iconDescription: String,
  action: () -> Unit,
) {
  val interactionSource = remember {
    MutableInteractionSource()
  }
  val pressed by interactionSource.collectIsPressedAsState()
  val infinite = rememberInfiniteTransition()

  val multiplier = remember { listOf(-1, 1).random() }
  val rotationStart = remember { (2..4).random() * multiplier }
  val rotationEnd = remember { -rotationStart }
  val duration = remember { listOf(1500, 2000, 2500).random() }

  val rotation by infinite.animateFloat(
    initialValue = rotationStart.toFloat(),
    targetValue = rotationEnd.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(duration, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  val navArrowTranslation by infinite.animateFloat(
    initialValue = 0f,
    targetValue = 8f,
    animationSpec = infiniteRepeatable(
      animation = tween(1500, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  val scale by animateFloatAsState(
    targetValue = if (pressed) 0.9f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessLow
    )
  )

  Box(
    modifier = Modifier
      .graphicsLayer(scaleX = scale, scaleY = scale, rotationZ = rotation)
      .width(40.dp)
      .height(40.dp)
      .clickable(interactionSource = interactionSource, indication = null) { action() },
    contentAlignment = Alignment.Center
  ) {
    Image(
      modifier = Modifier.size(30.dp),
      painter = painter,
      contentDescription = iconDescription,
      colorFilter = ColorFilter.tint(color = iconTint)
    )
    Box(
      modifier = Modifier
        .graphicsLayer(translationX = navArrowTranslation)
        .size(10.dp)
        .background(color = iconTint, shape = RoundedCornerShape(3.dp))
        .align(Alignment.BottomEnd),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        modifier = Modifier.size(8.dp),
        imageVector = Icons.Rounded.ArrowForward,
        tint = SpaceCadetDark,
        contentDescription = "Nav Button"
      )
    }
  }
}


@Composable
fun IconDeleteButton(action: () -> Unit) {
  val interactionSource = remember {
    MutableInteractionSource()
  }
  val pressed by interactionSource.collectIsPressedAsState()
  val infinite = rememberInfiniteTransition()
  val scope = rememberCoroutineScope()

  val multiplier = remember { listOf(-1, 1).random() }
  val rotationStart = remember { (4..8).random() * multiplier }
  val rotationEnd = remember { -rotationStart }
  val duration = remember { listOf(1500, 2000, 2500).random() }

  val scale by animateFloatAsState(
    targetValue = if (pressed) 0.9f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioLowBouncy,
      stiffness = Spring.StiffnessLow
    )
  )

  val rotation by infinite.animateFloat(
    initialValue = rotationStart.toFloat(),
    targetValue = rotationEnd.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = duration, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  val lidTranslationY = remember {
    Animatable(0f)
  }

  val canRotation = remember {
    Animatable(0f)
  }

  val canTranslationY = remember {
    Animatable(0f)
  }

  val lidPathData =
    PathParser().parsePathString("M21,5c0,0.55 -0.45,1 -1,1H4C3.45,6 3,5.55 3,5s0.45,-1 1,-1h7V3c0,-0.55 0.45,-1 1,-1s1,0.45 1,1v1h7C20.55,4 21,4.45 21,5z")
      .toNodes()
  val canPathData =
    PathParser().parsePathString("M4.52,7.5l1.34,12.08C6.01,20.96 7.19,22 8.59,22h6.82c1.4,0 2.58,-1.04 2.73,-2.42L19.48,7.5H4.52zM11,18c0,0.55 -0.45,1 -1,1s-1,-0.45 -1,-1v-6c0,-0.55 0.45,-1 1,-1s1,0.45 1,1V18zM15,18c0,0.55 -0.45,1 -1,1s-1,-0.45 -1,-1v-6c0,-0.55 0.45,-1 1,-1s1,0.45 1,1V18z")
      .toNodes()

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

    Group(
      name = "CanVector",
      pivotX = viewportWidth / 2,
      pivotY = viewportHeight / 2,
      translationY = canTranslationY.value,
      rotation = canRotation.value
    ) {
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

  Box(
    modifier = Modifier
      .graphicsLayer(scaleX = scale, scaleY = scale, rotationZ = rotation)
      .size(40.dp)
      .clip(shape = RoundedCornerShape(16.dp))
      .clickable(
        interactionSource = interactionSource,
        indication = null
      ) {
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
    Image(
      modifier = Modifier.size(30.dp),
      painter = iconVector,
      colorFilter = ColorFilter.tint(color = PopRed),
      contentDescription = "Reset"
    )
  }
}

@Composable
fun SuperButton(state: HydrationState, action: () -> Unit) {
  val interactionSource = remember { MutableInteractionSource() }
  val pressed by interactionSource.collectIsPressedAsState()

  val shape = RoundedCornerShape(20.dp)
  val size = 80.dp

  val buttonScale by animateFloatAsState(
    targetValue = if (pressed) 0.9f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioLowBouncy,
      stiffness = Spring.StiffnessLow
    )
  )

  val shadowScale by animateFloatAsState(
    targetValue = if (pressed) 1.3f else 1f,
    animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
  )
  val shadowAlpha by animateFloatAsState(
    targetValue = if (pressed) 0.9f else 0f,
    animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
  )
  val shadowBlur by animateDpAsState(
    targetValue = if (pressed) 24.dp else 8.dp,
    animationSpec = tween(durationMillis = 1000, easing = EaseInOut)
  )

  val red by animateColorAsState(
    targetValue = if (state.drank > 0) WispyWhite else MaterialRed,
    animationSpec = tween(300, easing = EaseInOut)
  )
  val yellow by animateColorAsState(
    targetValue = if (state.drank > 0) WispyWhite else MaterialYellow,
    animationSpec = tween(300, easing = EaseInOut)
  )
  val green by animateColorAsState(
    targetValue = if (state.drank > 0) WispyWhite else MaterialGreen,
    animationSpec = tween(300, easing = EaseInOut)
  )
  val blue by animateColorAsState(
    targetValue = if (state.drank > 0) WispyWhite else MaterialBlue,
    animationSpec = tween(300, easing = EaseInOut)
  )
  val purple by animateColorAsState(
    targetValue = if (state.drank > 0) WispyWhite else MaterialPurple,
    animationSpec = tween(300, easing = EaseInOut)
  )

  val gradient = Brush.sweepGradient(
    colors = listOf(
      red,
      yellow,
      green,
      blue,
      purple,
      red
    )
  )

  Box(
    modifier = Modifier
      .graphicsLayer(
        scaleX = buttonScale,
        scaleY = buttonScale
      )
      .clickable(
        interactionSource = interactionSource,
        indication = null
      ) { action() }
  ) {
    Box(
      modifier = Modifier
        .graphicsLayer(scaleX = shadowScale, scaleY = shadowScale)
        .size(size)
        .blur(radius = shadowBlur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
        .drawBehind {
          drawRoundRect(
            brush = gradient,
            cornerRadius = CornerRadius(20.dp.toPx()),
            alpha = shadowAlpha,
          )
        },
    )
    Box(
      modifier = Modifier
        .size(size)
        .background(
          shape = shape,
          color = SpaceCadet
        )
        .border(width = 2.dp, brush = gradient, shape = shape)
        .clip(shape),
      contentAlignment = Alignment.Center
    ) {
      Image(
        modifier = Modifier.size(40.dp),
        painter = painterResource(id = R.drawable.ic_plus_small),
        contentDescription = "Drink",
        colorFilter = ColorFilter.tint(WispyWhite)
      )
    }
  }
}

@Composable
fun DrinkDisplay(state: HydrationState) {
  val infiniteTransition = rememberInfiniteTransition()
  val rotation by infiniteTransition.animateFloat(
    initialValue = -4f,
    targetValue = 4f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1500, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )
  Box(
    modifier = Modifier
      .graphicsLayer(rotationZ = rotation)
      .wrapContentSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(text = "${state.drank} oz", fontSize = 20.sp, color = WispyWhite)
  }
}

@Preview
@Composable
fun SuperButtonPreview() {
  HydroHomieTheme {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(color = SpaceCadetDark),
      verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      NavButton(
        iconTint = PopYellow,
        painter = painterResource(id = R.drawable.ic_adjustments_horizontal),
        iconDescription = "Settings",
        action = {}
      )
      SuperButton(state = HydrationState(), {})
      IconDeleteButton {}
      DrinkDisplay(state = HydrationState(drank = 16))
    }
  }
}