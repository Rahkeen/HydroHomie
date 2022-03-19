package com.rikin.hydrohomie.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HydroIconButton(
  backgroundColor: Color,
  iconTint: Color,
  icon: ImageVector,
  iconDescription: String,
  action: () -> Unit,
) {


  Box(
    modifier = Modifier
      .width(ButtonWidth)
      .height(ButtonHeight)
      .background(
        shape = RoundedCornerShape(16.dp),
        brush = Brush.verticalGradient(colors = listOf(backgroundColor.copy(alpha = 0.7f), backgroundColor))
      )
      .clip(RoundedCornerShape(16.dp))
      .clickable { action() },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.size(20.dp),
      imageVector = icon,
      contentDescription = iconDescription,
      tint = iconTint
    )
  }
}

@Preview
@Composable
fun HydroIconButtonPreview() {
  HydroHomieTheme {
    HydroIconButton(
      backgroundColor = RadRed,
      iconTint = Color.White,
      icon = Icons.Rounded.Add,
      iconDescription = "Add",
      action = {}
    )
  }
}

val ButtonWidth = 60.dp
val ButtonHeight = 60.dp