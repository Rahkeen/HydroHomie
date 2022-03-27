package com.rikin.hydrohomie.features.streak.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import com.rikin.hydrohomie.design.BlueSkiesEnd
import com.rikin.hydrohomie.design.CoolBlue
import com.rikin.hydrohomie.design.HydroHomieTheme

val DAYS = listOf("S", "M", "T", "W", "T", "F", "S")

@Composable
fun Streaks() {
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
      repeat(7) { index ->
        StreakDotTwo(filled = index < 6, DAYS[index])
      }
    }
  }
}

@Preview
@Composable
fun StreaksPreview() {
  HydroHomieTheme {
    Streaks()
  }
}

@Composable
fun StreakDotOne(filled: Boolean) {
  val brushColor = when (filled) {
    true -> {
      Brush.horizontalGradient(
        colors = listOf(
          CoolBlue,
          BlueSkiesEnd
        )
      )
    }
    false -> {
      Brush.horizontalGradient(
        colors = listOf(Color.Gray, Color.Gray)
      )
    }
  }

  Canvas(modifier = Modifier.size(10.dp)) {
    drawCircle(
      brush = brushColor
    )
  }
}

@Composable
fun StreakDotTwo(filled: Boolean, dayLetter: String) {
  val brushColor = when (filled) {
    true -> {
      Brush.verticalGradient(
        colors = listOf(
          CoolBlue,
          BlueSkiesEnd
        )
      )
    }
    false -> {
      Brush.horizontalGradient(
        colors = listOf(Color.Gray, Color.Gray)
      )
    }
  }

  val emoji = when (filled) {
    true -> ""
    false -> "😵"
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Box(
      modifier = Modifier
        .width(30.dp)
        .height(60.dp)
        .background(
          brush = brushColor,
          shape = RoundedCornerShape(8.dp)
        ),
      contentAlignment = Alignment.Center
    ) {
      Text(text = emoji)
    }
    Text(text = dayLetter, style = MaterialTheme.typography.caption)
  }
}
