package com.rikin.hydrohomie.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilledButton(
  backgroundColor: Color,
  textColor: Color,
  text: String,
  action: () -> Unit,
) {
  Box(
    modifier = Modifier
      .width(80.dp)
      .height(60.dp)
      .background(color = backgroundColor)
      .clickable { action() },
    contentAlignment = Alignment.Center
  ) {
    Text(text = text, color = textColor, style = MaterialTheme.typography.body1)
  }
}

@Preview
@Composable
fun FilledButtonPreview() {
  HydroHomieTheme {
    FilledButton(
      backgroundColor = RadRed,
      textColor = Color.White,
      text = "💧",
      action = {}
    )
  }
}