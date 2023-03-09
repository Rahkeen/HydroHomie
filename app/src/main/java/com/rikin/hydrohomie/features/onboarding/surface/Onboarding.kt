package com.rikin.hydrohomie.features.onboarding.surface

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.common.domain.AppAction
import com.rikin.hydrohomie.design.DrinkDisplay
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.IconDeleteButton
import com.rikin.hydrohomie.design.NavButton
import com.rikin.hydrohomie.design.SpaceCadet
import com.rikin.hydrohomie.design.SuperButton
import com.rikin.hydrohomie.design.ThemeThree
import com.rikin.hydrohomie.design.ThemeTwo
import com.rikin.hydrohomie.design.WaterBlue
import com.rikin.hydrohomie.design.WaterGradient
import com.rikin.hydrohomie.design.WispyWhite
import com.rikin.hydrohomie.features.hydration.common.domain.HydrationState
import com.rikin.hydrohomie.features.hydration.common.surface.NewWaterContainer
import com.rikin.hydrohomie.features.settings.common.domain.NotificationStatus
import com.rikin.hydrohomie.features.settings.common.domain.SettingsState
import com.rikin.hydrohomie.features.settings.common.surface.DrinkSizeSelectionGroup
import com.rikin.hydrohomie.features.settings.common.surface.NewGoalSlider

@ExperimentalAnimationApi
@Preview
@Composable
fun HydrationOnboarding() {
  var step by remember { mutableStateOf(0) }
  HydroHomieTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(color = SpaceCadet),
      contentAlignment = Alignment.Center
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {

        // Text Section

        AnimatedContent(
          modifier = Modifier
            .fillMaxWidth()
            .weight(0.2f),
          targetState = step,
          transitionSpec = {
            fadeIn(
              animationSpec = tween(
                delayMillis = 600,
                durationMillis = 500,
                easing = LinearEasing
              )
            ) with fadeOut(
              animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
              )
            )
          }
        ) { state ->
          when (state) {
            0 -> {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Welcome to Hydro Homie", color = WispyWhite, fontSize = 20.sp)
              }
            }

            1 -> {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "This is Step 1", color = WispyWhite, fontSize = 20.sp)
              }
            }

            2 -> {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "This is Step 2", color = WispyWhite, fontSize = 20.sp)
              }
            }

            3 -> {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "This is Step 3", color = WispyWhite, fontSize = 20.sp)
              }
            }

            4 -> {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "This is Step 4", color = WispyWhite, fontSize = 20.sp)
              }
            }

            5 -> {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "This is Step 5", color = WispyWhite, fontSize = 20.sp)
              }
            }
          }
        }

        // Mini Content Section

        Box(
          modifier = Modifier
            .weight(0.6f)
            .aspectRatio(9f / 16, matchHeightConstraintsFirst = true)
            .background(
              color = Color.DarkGray,
              shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
          contentAlignment = Alignment.Center
        ) {
          // Hydration
          NewWaterContainer(
            state = HydrationState(
              drank = if (step == 3 || step > 5) 0 else 32,
              goal = 64
            )
          )
          Column(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            OnboardingSlideIn(show = step == 5) {
              NavButton(
                iconTint = ThemeTwo,
                buttonSize = 30.dp,
                iconSize = 20.dp,
                navIconButtonSize = 8.dp,
                navIconSize = 6.dp,
                painter = painterResource(id = R.drawable.ic_calendar_days),
                iconDescription = "Streaks",
                action = {
                }
              )
            }
            OnboardingSlideIn(show = step in 4..5) {
              NavButton(
                iconTint = ThemeThree,
                buttonSize = 30.dp,
                iconSize = 20.dp,
                navIconButtonSize = 8.dp,
                navIconSize = 6.dp,
                painter = painterResource(id = R.drawable.ic_adjustments_horizontal),
                iconDescription = "Settings",
                action = {
                }
              )
            }
            Spacer(modifier = Modifier.height(48.dp))
            OnboardingSlideIn(show = step in 3..5) {
              DrinkDisplay(fontSize = 12.sp, state = HydrationState(drank = 0, goal = 64))
            }
            OnboardingSlideIn(show = step in 2..5) {
              IconDeleteButton(buttonSize = 30.dp, iconSize = 20.dp) {}
            }
            OnboardingSlideIn(show = step in 1..5) {
              SuperButton(
                state = HydrationState(drank = 0, goal = 64),
                size = 40.dp,
                iconSize = 20.dp,
                shape = RoundedCornerShape(8.dp),
                borderWidth = 1.dp,
                action = {}
              )
            }
          }
          // Settings
          Column {
            OnboardingSlideIn(show = step > 5, right = false) {
              NewGoalSlider(
                low = 0,
                high = 128,
                scale = 0.75f,
                progress = 0.5f,
                label = "Goal",
                update = {}
              )
            }
            OnboardingSlideIn(show = step > 6, right = false) {
              DrinkSizeSelectionGroup(
                drinks = SettingsState(
                  personalGoal = 64,
                  defaultDrinkSize = 8,
                  notificationStatus = NotificationStatus.Disabled
                ).drinkSizes,
                onboarding = true,
                action = {}
              )
            }
          }

          // Streaks
        }

        // Onboarding Button Section

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .weight(0.2f),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          OnboardingButton(
            style = Style.Secondary,
            text = "Skip",
            action = {}
          )
          OnboardingButton(
            style = Style.Primary,
            text = "Next",
            action = {
              if (step == 7) {
                step = 0
              } else {
                step += 1
              }
            }
          )
        }
      }
    }
  }
}

@Composable
fun OnboardingSlideIn(show: Boolean, right: Boolean = true, content: @Composable BoxScope.() -> Unit) {
  val multiplier = if (right) 1 else -1
  val translationX by animateFloatAsState(
    targetValue = if (show) 0f else 400f * multiplier,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioLowBouncy,
      stiffness = Spring.StiffnessLow
    ),
    label = "OnboardingSlideIn translationX"
  )
  val alpha by animateFloatAsState(
    targetValue = if (show) 1f else 0f,
    animationSpec = tween(durationMillis = 100, easing = EaseInOut),
    label = "OnboardingSlideIn alpha"
  )
  Box(
    modifier = Modifier.graphicsLayer(translationX = translationX, alpha = alpha)
  ) {
    content()
  }
}

@Preview
@Composable
fun OnboardingSlideInPreview() {
  HydroHomieTheme {
    OnboardingSlideIn(show = true) {
      SuperButton(state = HydrationState(0, 64)) {}
    }
  }
}

enum class Style {
  Primary,
  Secondary
}

@Composable
fun OnboardingButton(
  modifier: Modifier = Modifier,
  style: Style = Style.Primary,
  text: String,
  action: () -> Unit
) {
  when (style) {
    Style.Primary -> {
      Box(
        modifier = modifier
          .width(80.dp)
          .height(60.dp)
          .background(brush = WaterGradient, shape = RoundedCornerShape(16.dp))
          .clip(RoundedCornerShape(16.dp))
          .clickable { action() },
        contentAlignment = Alignment.Center
      ) {
        Text(text = text, color = WispyWhite, fontSize = 20.sp)
      }
    }

    Style.Secondary -> {
      Box(
        modifier = modifier
          .width(80.dp)
          .height(60.dp)
          .clip(RoundedCornerShape(16.dp))
          .clickable { action() },
        contentAlignment = Alignment.Center
      ) {
        Text(text = text, color = WaterBlue, fontSize = 20.sp)
      }
    }
  }
}

@Preview
@Composable
fun PrimaryOnboardingButtonPreview() {
  HydroHomieTheme {
    Box {
      OnboardingButton(style = Style.Primary, text = "Next") {}
    }
  }
}

@Preview
@Composable
fun SecondaryOnboardingButtonPreview() {
  HydroHomieTheme {
    Box {
      OnboardingButton(style = Style.Secondary, text = "Skip") {}
    }
  }
}



