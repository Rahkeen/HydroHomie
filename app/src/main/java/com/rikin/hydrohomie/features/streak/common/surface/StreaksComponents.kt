package com.rikin.hydrohomie.features.streak.common.surface

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.IconSize
import com.rikin.hydrohomie.design.PopRed
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.SpaceCadetDark
import com.rikin.hydrohomie.design.ThemeOne
import com.rikin.hydrohomie.design.WaterGradient
import com.rikin.hydrohomie.design.WispyWhite
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState

@Composable
fun StreakCup(hydrationState: HydrationState, dayLetter: String, isToday: Boolean = false) {

  val emoji = when {
    hydrationState.drank > 0 -> ""
    else -> "😵"
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
          .fillMaxHeight(hydrationState.percent.toFloat())
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
    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.Bottom
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
fun CircleIcon(icon: ImageVector, color: Color) {
  Icon(
    modifier = Modifier
      .size(IconSize)
      .drawBehind {
        drawCircle(
          color = color.copy(alpha = 0.3F),
          radius = ((IconSize + ElementPadding).toPx()) / 2
        )
      },
    imageVector = icon,
    tint = color,
    contentDescription = ""
  )
}

@Preview(showBackground = true)
@Composable
fun WithIconPreview() {
  HydroHomieTheme {
    Surface {
      WithIcon(icon = Icons.Rounded.Delete, iconTint = PopRed) {
        Text(
          text = "Delete",
          modifier = Modifier.wrapContentSize(),
          style = MaterialTheme.typography.caption
        )
      }
    }
  }
}
