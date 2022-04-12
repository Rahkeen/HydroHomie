package com.rikin.hydrohomie.features.streak.surface

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.app.domain.Weekday
import com.rikin.hydrohomie.design.BlueSkiesEnd
import com.rikin.hydrohomie.design.CoolBlue
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.features.hydration.domain.HydrationState
import com.rikin.hydrohomie.features.streak.domain.StreakState

val DAYS = listOf("M", "T", "W", "T", "F", "S", "S")

@Composable
fun Streaks(state: StreakState) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(
        space = 8.dp,
        alignment = Alignment.CenterHorizontally
      ),
      verticalAlignment = Alignment.CenterVertically
    ) {
      state.currentWeek.forEachIndexed { index, hydrationState ->
        StreakCup(
          hydrationState = hydrationState,
          dayLetter = DAYS[index],
          isToday = index == state.currentDay.ordinal
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun StreaksPreview() {
  HydroHomieTheme {
    Streaks(
      state = StreakState(
        currentDay = Weekday.Sunday,
        currentWeek = listOf(
          HydrationState(drank = 64.0),
          HydrationState(drank = 64.0),
          HydrationState(drank = 64.0),
          HydrationState(drank = 64.0),
          HydrationState(drank = 64.0),
          HydrationState(drank = 64.0),
          HydrationState(drank = 0.0),
        )
      )
    )
  }
}

@Composable
fun StreakCup(hydrationState: HydrationState, dayLetter: String, isToday: Boolean = false) {

  val brushColor = Brush.verticalGradient(
    colors = listOf(
      CoolBlue,
      BlueSkiesEnd
    )
  )

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
          .size(24.dp)
          .graphicsLayer { translationY = indicatorY.value },
        imageVector = Icons.Rounded.ArrowDropDown,
        contentDescription = ""
      )
    } else {
      Spacer(modifier = Modifier.size(24.dp))
    }
    Box(
      modifier = Modifier
        .width(30.dp)
        .height(60.dp)
        .background(
          color = Color.Gray,
          shape = RoundedCornerShape(8.dp)
        ),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(hydrationState.percent.toFloat())
          .background(
            brush = brushColor,
            shape = RoundedCornerShape(8.dp)
          )
          .align(Alignment.BottomCenter)
      )
      Text(text = emoji, fontSize = 16.sp)
    }
    Text(text = dayLetter, style = MaterialTheme.typography.caption)
  }
}

@Preview(showBackground = true)
@Composable
fun StreakCupPreview() {
  HydroHomieTheme {
    StreakCup(hydrationState = HydrationState(), dayLetter = "M", isToday = true)
  }
}
