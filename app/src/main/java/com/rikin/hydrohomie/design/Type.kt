package com.rikin.hydrohomie.design

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.R

//private val mukta = FontFamily(
//  Font(resId = R.font.mukta_regular, weight = FontWeight.Normal),
//  Font(resId = R.font.mukta_medium, weight = FontWeight.Medium),
//  Font(resId = R.font.mukta_semibold, weight = FontWeight.SemiBold),
//  Font(resId = R.font.mukta_bold, weight = FontWeight.Bold)
//)

val quicksand = FontFamily(
  Font(resId = R.font.quicksand_semibold, weight = FontWeight.SemiBold),
  Font(resId = R.font.quicksand_semibold, weight = FontWeight.Normal)
)

val Typography = Typography(
  defaultFontFamily = quicksand,
  body1 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.SemiBold,
    fontSize = 26.sp
  ),
  body2 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp
  ),
  h1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 48.sp
  ),
  h4 = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.Normal,
    fontSize = 36.sp
  ),
  caption = TextStyle(
    fontFamily = quicksand,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
  )
  /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)