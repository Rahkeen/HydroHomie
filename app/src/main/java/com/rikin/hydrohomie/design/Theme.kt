package com.rikin.hydrohomie.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  background = BoldBlack,
  surface = BlackRussian
)

private val LightColorPalette = lightColors(
  background = WispyWhite,
  surface = Porcelain
)

@Composable
fun HydroHomieTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = DarkColorPalette
  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}