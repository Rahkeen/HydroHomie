package com.rikin.hydrohomie.design

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.R

val quicksand = FontFamily(
  Font(resId = R.font.quicksand_semibold, weight = FontWeight.SemiBold),
  Font(resId = R.font.quicksand_semibold, weight = FontWeight.Normal)
)

val Typography = Typography(
  defaultFontFamily = quicksand,
  h1 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 48.sp
  ),
  h2 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 36.sp
  ),
  body1 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 26.sp
  ),
  body2 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp
  ),
  caption = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
  )
)