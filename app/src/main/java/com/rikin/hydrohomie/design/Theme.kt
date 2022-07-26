package com.rikin.hydrohomie.design

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  background = SpaceCadetDark,
  surface = SpaceCadet
)

@Composable
fun HydroHomieTheme(content: @Composable () -> Unit) {
  val colors = DarkColorPalette
  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}