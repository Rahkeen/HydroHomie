package com.rikin.hydrohomie.features.streak.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.app.domain.HydrationState
import com.rikin.hydrohomie.design.BlueSkiesEnd
import com.rikin.hydrohomie.design.CoolBlue
import com.rikin.hydrohomie.design.HydroHomieTheme

val DAYS = listOf("S", "M", "T", "W", "T", "F", "S")

@Composable
fun Streaks(state: List<HydrationState>) {
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
      state.forEachIndexed { index, hydrationState ->
        StreakCup(hydrationState = hydrationState, dayLetter = DAYS[index])
      }
    }
  }
}

@Preview
@Composable
fun StreaksPreview() {
  HydroHomieTheme {
    Streaks(
      state = listOf(
        HydrationState(count = 8F),
        HydrationState(count = 8F),
        HydrationState(count = 8F),
        HydrationState(count = 8F),
        HydrationState(count = 8F),
        HydrationState(count = 8F),
        HydrationState(count = 0F),
      )
    )
  }
}

@Composable
fun StreakCup(hydrationState: HydrationState, dayLetter: String) {

  val brushColor = Brush.verticalGradient(
    colors = listOf(
      CoolBlue,
      BlueSkiesEnd
    )
  )

  val emoji = when {
    hydrationState.count > 0 -> ""
    else -> "😵"
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
          .fillMaxHeight(hydrationState.percent)
          .background(
            brush = brushColor,
            shape = RoundedCornerShape(8.dp)
          )
          .align(Alignment.BottomCenter)
      )
      Text(text = emoji)
    }
    Text(text = dayLetter, style = MaterialTheme.typography.caption)
  }
}
