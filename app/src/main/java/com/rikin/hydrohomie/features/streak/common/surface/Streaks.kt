package com.rikin.hydrohomie.features.streak.common.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.common.domain.Weekday
import com.rikin.hydrohomie.design.ComponentPadding
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.ThemeOne
import com.rikin.hydrohomie.design.ThemeThree
import com.rikin.hydrohomie.design.ThemeTwo
import com.rikin.hydrohomie.design.WispyWhite
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.features.streak.common.domain.StreakState

val DAYS = listOf("M", "T", "W", "T", "F", "S", "S")

@Composable
fun Streaks(state: StreakState) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background),
    verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    WeeklyStreak(state = state)
    WeeklyStats(state = state)
  }
}

@Composable
fun WeeklyStreak(state: StreakState, onboarding: Boolean = false) {
  val scale = remember(onboarding) { if (onboarding) 0.75f else 1f }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .graphicsLayer(scaleX = scale, scaleY = scale),
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

@Composable
fun WeeklyStats(state: StreakState, onboarding: Boolean = false) {
  val scale = remember(onboarding) { if (onboarding) 0.75f else 1f }
  val fraction = remember(onboarding) { if (onboarding) 0.8f else 0.6f }

  Column(
    modifier = Modifier
      .fillMaxWidth(fraction)
      .scale(scale),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ElementPadding)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      WithIcon(
        painter = painterResource(id = R.drawable.ic_bolt),
        iconTint = ThemeTwo
      ) {
        Text(
          text = "Streak",
          color = WispyWhite,
          style = MaterialTheme.typography.caption
        )
      }
      Text(
        text = "${state.currentStreak} d",
        color = ThemeTwo,
        style = MaterialTheme.typography.caption
      )
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      WithIcon(
        painter = painterResource(id = R.drawable.ic_clipboard),
        iconTint = ThemeOne
      ) {
        Text(
          text = "Completed",
          color = WispyWhite,
          style = MaterialTheme.typography.caption
        )
      }
      Text(
        text = "${state.completedDays} d",
        color = ThemeOne,
        style = MaterialTheme.typography.caption
      )
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      WithIcon(
        painter = painterResource(id = R.drawable.ic_arrow_trending_up),
        iconTint = ThemeThree
      ) {
        Text(
          text = "Total Drank",
          color = WispyWhite,
          style = MaterialTheme.typography.caption
        )
      }
      Text(
        text = "${state.totalDrank} oz",
        color = ThemeThree,
        style = MaterialTheme.typography.caption
      )
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
          HydrationState(drank = 32, goal = 64),
          HydrationState(drank = 48, goal = 64),
          HydrationState(drank = 36, goal = 64),
          HydrationState(drank = 64, goal = 64),
          HydrationState(drank = 64, goal = 64),
          HydrationState(drank = 64, goal = 64),
          HydrationState(drank = 0, goal = 64),
        )
      )
    )
  }
}
