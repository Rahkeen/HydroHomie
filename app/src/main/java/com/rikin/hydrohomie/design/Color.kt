package com.rikin.hydrohomie.design

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val CoolBlue = Color(0xFF2962ff)
val OzoneOrange = Color(0xFFF49052)
val OzoneOrangeDark = Color(0xFF8B4307)
val RadRed = Color(0xFFff4f7b)
val RadRedDark = Color(0xFF890000)
val Yellowstone = Color(0xFFFFE082)
val YellowstoneDark = Color(0xFF4d4e01)
val PlayaPurple = Color(0xFFBA68C8)
val PlayaPurpleDark = Color(0xFF4D1A57)
val GangstaGreen = Color(0xFF30c67c)
val GangstaGreenDark = Color(0xFF28532A)

val WispyWhite = Color(0xFFFFFBFE)
val BoldBlack = Color(0xFF1C1B1F)

val Porcelain = Color(0xFFF1F0F3)
val BlackRussian = Color(0xFF262628)

val JuicyOrangeStart = Color(0xffFF8008)
val JuicyOrangeEnd = Color(0xffFFC837)

val BlueSkiesStart = Color(0xff56CCF2)
val BlueSkiesEnd = Color(0xff2F80ED)

val PurpleDreamStart = Color(0xFF696EFF)
val PurpleDreamEnd = Color(0xFFF8ACFF)

val waterGradient = Brush.verticalGradient(
  colors = listOf(
    CoolBlue,
    BlueSkiesEnd
  )
)

val imageGradient = Brush.verticalGradient(
  colors = listOf(
    JuicyOrangeStart,
    JuicyOrangeEnd,
  )
)

val sliderGradient = Brush.horizontalGradient(
  colors = listOf(
    JuicyOrangeStart,
    JuicyOrangeEnd,
  )
)