package com.rikin.hydrohomie.features.streak.common.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rikin.hydrohomie.app.common.domain.Weekday
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.features.streak.common.domain.StreakState

val DAYS = listOf("M", "T", "W", "T", "F", "S", "S")

@Composable
fun Streaks(state: StreakState) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background),
    verticalArrangement = Arrangement.spacedBy(ElementPadding, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(
        space = ElementPadding,
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
