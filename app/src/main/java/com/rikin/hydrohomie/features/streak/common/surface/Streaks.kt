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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rikin.hydrohomie.app.common.domain.Weekday
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

    Spacer(modifier = Modifier.height(ElementPadding))

    Column(
      modifier = Modifier.fillMaxWidth(0.5F),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(ElementPadding)
    ) {
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        WithIcon(
          icon = Icons.Rounded.List,
          iconTint = ThemeOne
        ) {
          Text(
            text = "Streak",
            color = WispyWhite,
            style = MaterialTheme.typography.caption
          )
        }
        Text(
          text = "${state.consecutiveDays} d",
          color = ThemeOne,
          style = MaterialTheme.typography.caption
        )
      }
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        WithIcon(
          icon = Icons.Rounded.Done,
          iconTint = ThemeTwo
        ) {
          Text(
            text = "Completed",
            color = WispyWhite,
            style = MaterialTheme.typography.caption
          )
        }
        Text(
          text = "${state.completedDays} d",
          color = ThemeTwo,
          style = MaterialTheme.typography.caption
        )
      }
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        WithIcon(
          icon = Icons.Rounded.Info,
          iconTint = ThemeThree
        ) {
          Text(
            text = "Total Drank",
            color = WispyWhite,
            style = MaterialTheme.typography.caption
          )
        }
        Text(
          text = "${state.totalDrank.toInt()} oz",
          color = ThemeThree,
          style = MaterialTheme.typography.caption
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
