package com.rikin.hydrohomie.features.hydration.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.rikin.hydrohomie.app.domain.AppAction
import com.rikin.hydrohomie.app.domain.AppState
import com.rikin.hydrohomie.design.BlueSkiesEnd
import com.rikin.hydrohomie.design.BlueSkiesStart
import com.rikin.hydrohomie.design.HydroIconButton
import com.rikin.hydrohomie.design.OzoneOrange
import com.rikin.hydrohomie.design.OzoneOrangeDark
import com.rikin.hydrohomie.design.RadRed
import com.rikin.hydrohomie.design.RadRedDark

@Composable
fun Hydration(state: AppState, actions: (AppAction) -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(fraction = state.count / state.goal)
        .background(brush = Brush.verticalGradient(colors = listOf(BlueSkiesStart, BlueSkiesEnd)))
        .align(Alignment.BottomCenter)
    )

    Column(
      modifier = Modifier
        .wrapContentSize()
        .padding(end = 16.dp)
        .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(22.dp))
        .padding(8.dp)
        .align(Alignment.CenterEnd),
      verticalArrangement = Arrangement.spacedBy(
        space = 8.dp,
        alignment = Alignment.CenterVertically
      ),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      HydroIconButton(
        backgroundColor = OzoneOrange,
        iconTint = OzoneOrangeDark,
        icon = Icons.Rounded.ThumbUp,
        iconDescription = "Add",
        action = {
          actions(
            AppAction.Drink
          )
        }
      )

      HydroIconButton(
        backgroundColor = RadRed,
        iconTint = RadRedDark,
        icon = Icons.Rounded.Delete,
        iconDescription = "Clear",
        action = {
          actions(
            AppAction.Reset
          )
        }
      )
    }
  }
}
