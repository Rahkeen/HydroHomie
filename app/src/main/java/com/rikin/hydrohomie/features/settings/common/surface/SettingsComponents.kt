package com.rikin.hydrohomie.features.settings.common.surface

import android.graphics.Path
import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.design.ComponentPadding
import com.rikin.hydrohomie.design.ElementPadding
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.OceanBlue
import com.rikin.hydrohomie.design.PopRed
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.SpaceCadetDark
import com.rikin.hydrohomie.design.ThemeSliderPrimary
import com.rikin.hydrohomie.design.Typography
import com.rikin.hydrohomie.design.WispyWhite
import com.rikin.hydrohomie.features.settings.common.domain.DrinkSizeState
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
    verticalArrangement = Arrangement.spacedBy(ElementPadding)
  ) {
    Text(
      modifier = Modifier.padding(start = ElementPadding),
      text = sliderName,
      style = Typography.caption,
      color = WispyWhite
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      NewSquigglySlider(
        modifier = Modifier.weight(0.8f),
        state = progress,
        update = { percent ->
          val amount =
            onUpdate(percent)
        }
      )
      Text(
        modifier = Modifier
          .weight(0.2f)
          .padding(start = ComponentPadding),
        text = "$current oz",
        style = Typography.caption,
        textAlign = TextAlign.Start,
        color = color
      )
    }
  }
}

@Composable
fun NewGoalSlider(
  low: Int,
  high: Int,
  progress: Float,
  label: String,
  color: Color = ThemeSliderPrimary,
  update: (Float) -> Unit
) {
  val display = remember(progress) { (progress * (high - low)).toInt() }

  Column(
    verticalArrangement = Arrangement.spacedBy(ElementPadding)
  ) {
    Text(
      modifier = Modifier.padding(start = ElementPadding),
      text = label,
      style = MaterialTheme.typography.caption,
      color = WispyWhite
    )
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      NewSquigglySlider(
        modifier = Modifier.weight(0.85f),
        state = progress,
        update = update
      )
      Text(
        modifier = Modifier.weight(0.15f),
        text = "$display oz",
        style = MaterialTheme.typography.caption,
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

@Preview(showBackground = true)
@Composable
fun NewGoalSliderPreview() {
  HydroHomieTheme {
    Box(modifier = Modifier.background(color = SpaceCadet)) {
      NewGoalSlider(
        low = 0,
        high = 200,
        progress = 0.5f,
        label = "Goal",
        update = {}
      )
    }
  }
}

@Composable
fun DrinkSizeSelectionGroup(drinks: List<DrinkSizeState>, action: (DrinkSizeState) -> Unit) {
  Column(
    verticalArrangement = Arrangement.spacedBy(ComponentPadding),
  ) {
    Text(
      "Default Drink Size",
      modifier = Modifier.padding(start = ElementPadding),
      style = MaterialTheme.typography.caption
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      drinks.forEach { state ->
        if (state.custom) {
          CustomDrinkSizeSelection(
            state = state,
            update = action
          )
        } else {
          DrinkSizeSelection(
            state = state,
            select = action
          )
        }
      }
    }
  }
}

@Composable
fun DrinkSizeSelection(
  state: DrinkSizeState,
  select: (DrinkSizeState) -> Unit
) {

  val progress by animateFloatAsState(
    targetValue = if (state.selected) 0f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioNoBouncy,
      stiffness = Spring.StiffnessVeryLow
    )
  )

  val scale by animateFloatAsState(
    targetValue = if (state.selected) 1.1f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessLow
    )
  )

  val width = 75.dp
  val height = 125.dp

  val color = OceanBlue

  HydroHomieTheme {
    Box(
      modifier = Modifier
        .graphicsLayer(scaleX = scale, scaleY = scale)
        .clip(RoundedCornerShape(16.dp))
        .clickable { select(state) }
    ) {
      Box(
        modifier = Modifier
          .width(width)
          .height(height)
          .background(color = color),
        contentAlignment = Alignment.Center
      ) {
        Text(state.label, color = Color.White, fontSize = 16.sp)
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
            .background(color = SpaceCadet)
            .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = color),
          contentAlignment = Alignment.Center
        ) {
          Text(state.label, color = color, fontSize = 16.sp)
        }
      }
    }
  }
}

@Composable
fun CustomDrinkSizeSelection(
  state: DrinkSizeState,
  update: (DrinkSizeState) -> Unit
) {
  val max = 128f
  var progress by remember {
    mutableStateOf((max - state.amount) / max)
  }
  val scale by animateFloatAsState(
    targetValue = if (state.selected) 1.1f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessLow
    )
  )

  val amount = ((1 - progress) * max).roundToInt()
  val width = 75.dp
  val height = 125.dp

  val color = PopRed

  Box(
    modifier = Modifier
      .graphicsLayer(scaleX = scale, scaleY = scale)
      .clip(RoundedCornerShape(16.dp))
      .pointerInput(Unit) {
        forEachGesture {
          awaitPointerEventScope {
            awaitFirstDown()
            do {
              val event = awaitPointerEvent()
              val clampedY = event.changes.last().position.y.coerceIn(
                minimumValue = 0f,
                maximumValue = height.toPx()
              )
              val normalizedY = clampedY / height.toPx()
              Log.d("Custom Button", "Dragged")
              progress = normalizedY
            } while (event.changes.none { it.changedToUp() })
            update(state.copy(amount = ((1 - progress) * max).roundToInt()))
          }
        }
      }
  ) {
    Box(
      modifier = Modifier
        .width(width)
        .height(height)
        .background(color = color),
      contentAlignment = Alignment.Center
    ) {
      Text("$amount oz", color = Color.White, fontSize = 16.sp)
    }

    Box(
      modifier = Modifier.clip(ClipShape(progress))
    ) {
      Box(
        modifier = Modifier
          .width(width)
          .height(height)
          .background(color = SpaceCadet)
          .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = color),
        contentAlignment = Alignment.Center
      ) {
        Text("$amount oz", color = color, fontSize = 16.sp)
      }
    }
  }
}

@Preview
@Composable
fun DrinkSizeSelectionPlayground() {
  var drinks by remember {
    mutableStateOf(
      listOf(
        DrinkSizeState(0, 8, true),
        DrinkSizeState(1, 16, false),
        DrinkSizeState(2, 32, false),
        DrinkSizeState(3, 20, false, custom = true)
      )
    )
  }

  fun selectDrinkSize(selectedSize: DrinkSizeState) {
    drinks = drinks.map { state ->
      state.copy(selected = state == selectedSize)
    }
  }

  HydroHomieTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(color = SpaceCadetDark),
      contentAlignment = Alignment.Center
    ) {
      DrinkSizeSelectionGroup(drinks = drinks, action = ::selectDrinkSize)
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
