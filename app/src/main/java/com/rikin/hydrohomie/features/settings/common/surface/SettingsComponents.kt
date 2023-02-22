package com.rikin.hydrohomie.features.settings.common.surface

import android.graphics.Path
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.design.ComponentPadding
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.PopRed
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.SpaceCadetDark
import com.rikin.hydrohomie.design.ThemeSliderPrimary
import com.rikin.hydrohomie.design.Typography
import com.rikin.hydrohomie.design.WaterBlue
import com.rikin.hydrohomie.design.WaterGradient
import com.rikin.hydrohomie.design.WispyWhite

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

@Composable
fun DrinkSizeSelection(
  index: Int,
  size: String,
  selected: Boolean,
  select: (Int) -> Unit
) {
  val progress by animateFloatAsState(
    targetValue = if (selected) 0f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioNoBouncy,
      stiffness = Spring.StiffnessVeryLow
    )
  )

  val width = 75.dp
  val height = 125.dp

  HydroHomieTheme {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .clickable { select(index) }
    ) {
      Box(
        modifier = Modifier
          .width(width)
          .height(height)
          .background(brush = WaterGradient),
        contentAlignment = Alignment.Center
      ) {
        Text(size, color = Color.White)
      }

      Box(
        modifier = Modifier
          .width(width)
          .height(height)
          .clip(ClipShape(progress))
      ) {
        Box(
          modifier = Modifier
            .width(width)
            .height(height)
            .background(color = Color.LightGray)
            .border(width = 2.dp, shape = RoundedCornerShape(16.dp), brush = WaterGradient),
          contentAlignment = Alignment.Center
        ) {
          Text(size, color = WaterBlue)
        }
      }
    }
  }
}

@Preview
@Composable
fun DrinkSizeSelectionPlayground() {
  var selectedIndex by remember {
    mutableStateOf(0)
  }
  HydroHomieTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(color = SpaceCadetDark),
      contentAlignment = Alignment.Center
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
      ) {
        DrinkSizeSelection(
          index = 0,
          size = "8oz",
          selected = selectedIndex == 0,
          { selectedIndex = it })
        DrinkSizeSelection(
          index = 1,
          size = "16oz",
          selected = selectedIndex == 1,
          { selectedIndex = it })
        DrinkSizeSelection(
          index = 2,
          size = "32oz",
          selected = selectedIndex == 2,
          { selectedIndex = it })
      }
    }
  }
}

class ClipShape(private val progress: Float = 0f) : Shape {
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density
  ): Outline {
    val path = Path().apply {
      addRect(
        0f,
        0f,
        size.width,
        size.height * progress,
        Path.Direction.CW
      )
    }
    return Outline.Generic(path.asComposePath())
  }
}
