package com.rikin.hydrohomie.design

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.design.LikeAnimationPaths.likeClickedPath
import com.rikin.hydrohomie.design.LikeAnimationPaths.likeDefaultPath
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object LikeAnimationPaths {
  val likeDefaultPath = PathParser().parsePathString(
    "M12.62,20.81C12.28,20.93 11.72,20.93 11.38,20.81C8.48,19.82 2,15.69 2,8.69C2,5.6 4.49,3.1 7.56,3.1C9.38,3.1 10.99,3.98 12,5.34C13.01,3.98 14.63,3.1 16.44,3.1C19.51,3.1 22,5.6 22,8.69C22,15.69 15.52,19.82 12.62,20.81Z"
  ).toNodes()

  val likeClickedPath = PathParser().parsePathString(
    "M12.62,20.81C12.28,20.93 11.72,20.93 11.38,20.81C8.48,19.82 2,15.69 2,8.69C2,5.6 4.49,3.1 7.56,3.1C9.38,3.1 10.99,3.98 12,5.34C13.01,3.98 14.63,3.1 16.44,3.1C19.51,3.1 22,5.6 22,8.69C22,15.69 15.52,19.82 12.62,20.81Z"
  ).toNodes()
}


//@Preview
@Composable
fun LikeAnimation() {
  var clicked = false

  val rotationAnimation = remember {
    Animatable(1f)
  }

  val scaleDefaultAnimation = remember {
    Animatable(1f)
  }

  val scaleClickedAnimation = remember {
    Animatable(0f)
  }

  val alphaAnimation = remember {
    Animatable(0f)
  }

  suspend fun clickedAnimation(clicked: Boolean) {
    val tweenSpec = tween<Float>(150, easing = LinearEasing)
    coroutineScope {
      launch {
        if (!clicked) {
          rotationAnimation.animateTo(0f)
        } else {
          rotationAnimation.animateTo(-25f , tweenSpec)
          rotationAnimation.animateTo(360f, animationSpec = tween(550))
        }
        launch {
          scaleDefaultAnimation.animateTo(if (clicked) 0f else 1f, animationSpec = tween(250))
        }
        launch {
          alphaAnimation.animateTo(if (clicked) 1f else 0f, animationSpec = tweenSpec)
        }
        launch {
          scaleClickedAnimation.animateTo(if (clicked) 1f else 0f, animationSpec = tween(350))
        }
      }
    }
  }


  val vectorPainter = rememberVectorPainter(
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
    autoMirror = true,
  ) { viewPortWidth, viewPortHeight ->
    Group(
      name = "likeVector",
      rotation = rotationAnimation.value,
      scaleX = scaleDefaultAnimation.value,
      scaleY = scaleDefaultAnimation.value,
      pivotX = viewPortWidth / 2,
      pivotY = viewPortHeight / 2
    ) {
      Group("likeDefault") {
        Path(
          pathData = likeDefaultPath,
          fill = SolidColor(Color.Transparent),
          strokeLineWidth = 1f,
          strokeAlpha = 0.8f,
          strokeLineCap = StrokeCap.Round,
          strokeLineJoin = StrokeJoin.Round,
          stroke = SolidColor(Color.White)
        )
      }
    }

    Group(
      name = "likeClickedVector",
      scaleX = scaleClickedAnimation.value,
      scaleY = scaleClickedAnimation.value,
      pivotX = viewPortWidth / 2,
      pivotY = viewPortHeight / 2
    ) {
      Group("likeClicked") {
        Path(
          pathData = likeClickedPath,
          fill = SolidColor(PopRed),
          strokeLineWidth = 1f,
          fillAlpha = alphaAnimation.value,
          strokeLineCap = StrokeCap.Round,
          strokeLineJoin = StrokeJoin.Round
        )
      }
    }
  }

  val coroutineScope = rememberCoroutineScope()
  Image(
    vectorPainter,
    contentDescription = "LikeAnimation",
    modifier = Modifier
      .fillMaxSize()
      .background(PopYellow)
      .pointerInput(Unit) {
        detectTapGestures {
          coroutineScope.launch {
            clicked = if (clicked) {
              clickedAnimation(true)
              !clicked
            } else {
              clickedAnimation(false)
              !clicked
            }
          }
        }
      }
  )
}

@Preview
@Composable
fun IconPaths() {
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
      .fillMaxSize()
      .background(color = WispyWhite),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = iconVector,
      modifier = Modifier
        .size(100.dp)
        .clickable(
          indication = null,
          interactionSource = remember { MutableInteractionSource() }
        ) {
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
      contentDescription = "Delete",
    )
  }
}
