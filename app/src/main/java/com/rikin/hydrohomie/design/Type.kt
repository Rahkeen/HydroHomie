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
    fontSize = 48.sp,
    color = WispyWhite
  ),
  h2 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 36.sp,
    color = WispyWhite
  ),
  body1 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 26.sp,
    color = WispyWhite
  ),
  body2 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    color = WispyWhite
  ),
  caption = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = WispyWhite
  )
)