package com.rikin.hydrohomie.features.settings.common.surface

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.rikin.hydrohomie.design.ComponentPadding
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.ThemeSliderPrimary
import com.rikin.hydrohomie.design.Typography
import com.rikin.hydrohomie.design.WispyWhite
import kotlin.math.roundToInt

@Composable
fun GoalSlider(
  low: Int,
  high: Int,
  current: Int,
  sliderName: String,
  color: Color = ThemeSliderPrimary,
  onUpdate: (Float) -> Unit
) {
  val progress by animateFloatAsState(targetValue = (current.toFloat() / high))

  Column(
    modifier = Modifier.padding(ComponentPadding),
    verticalArrangement = Arrangement.spacedBy(ElementPadding)
  ) {
    Text(
      modifier = Modifier.padding(start = ElementPadding),
      text = sliderName,
      style = Typography.caption,
      color = WispyWhite
    )
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      Arrangement.spacedBy(ComponentPadding),
      verticalAlignment = Alignment.CenterVertically
    ) {
      SquigglySlider(
        modifier = Modifier
          .weight(5F)
          .wrapContentHeight(),
        value = progress,
        low = low.toFloat(),
        high = high.toFloat(),
        color = color,
        animateWave = true,
        onValueChanged = { onUpdate(it) }
      )
      Text(
        modifier = Modifier.weight(1F),
        text = "$current oz",
        style = Typography.caption,
        textAlign = TextAlign.Start,
        color = color
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GoalSliderPreview() {
  HydroHomieTheme {
    Box(modifier = Modifier.background(color = SpaceCadet)) {
      GoalSlider(
        low = 30,
        high = 200,
        current = 64,
        sliderName = "Goal",
        onUpdate = {}
      )
    }
  }
}
