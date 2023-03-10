package com.rikin.hydrohomie.app.mavericks.suface

import android.os.Parcelable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.replace
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rikin.hydrohomie.app.mavericks.domain.AppViewModel
import com.rikin.hydrohomie.app.mavericks.suface.NavTarget.HydrationTarget
import com.rikin.hydrohomie.app.mavericks.suface.NavTarget.OnboardingTarget
import com.rikin.hydrohomie.app.mavericks.suface.NavTarget.SettingsTarget
import com.rikin.hydrohomie.app.mavericks.suface.NavTarget.StreaksTarget
import com.rikin.hydrohomie.app.platform.LocalIntegrationPoint
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.features.hydration.common.surface.Hydration
import com.rikin.hydrohomie.features.onboarding.surface.Onboarding
import com.rikin.hydrohomie.features.onboarding.surface.OnboardingStep
import com.rikin.hydrohomie.features.settings.common.surface.Settings
import com.rikin.hydrohomie.features.streak.common.surface.Streaks
import kotlinx.parcelize.Parcelize

@ExperimentalAnimationApi
@Composable
fun MavericksApp() {
  val systemUiController = rememberSystemUiController()

  SideEffect {
    systemUiController.setSystemBarsColor(
      color = Color.Transparent
    )
  }

  HydroHomieTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
      val viewModel: AppViewModel = mavericksActivityViewModel()
      val onboarded by viewModel.collectAsState { it.onboardingStep == OnboardingStep.Finished }
      NodeHost(integrationPoint = LocalIntegrationPoint.current) {
        RootNode(
          it, backStack = BackStack(
            initialElement = if (onboarded) HydrationTarget else OnboardingTarget,
            savedStateMap = it.savedStateMap
          )
        )
      }
    }
  }
}

@ExperimentalAnimationApi
class RootNode(
  buildContext: BuildContext,
  private val backStack: BackStack<NavTarget>
) : ParentNode<NavTarget>(
  buildContext = buildContext,
  navModel = backStack
) {

  @Composable
  override fun View(modifier: Modifier) {
    Children(
      modifier = modifier,
      navModel = backStack,
      transitionHandler = rememberBackstackSlider(
        transitionSpec = {
          spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
        }
      )
    )
  }

  override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
    return when (navTarget) {
      OnboardingTarget -> {
        node(buildContext) {
          val viewModel: AppViewModel = mavericksActivityViewModel()
          val state by viewModel.collectAsState { it.onboardingState }
          Onboarding(
            state = state,
            actions = viewModel::send,
            navigation = backStack::replace
          )
        }
      }

      HydrationTarget -> {
        node(buildContext) {
          val viewModel: AppViewModel = mavericksActivityViewModel()
          val state by viewModel.collectAsState { it.hydrationState }
          Hydration(
            state = state,
            actions = viewModel::send,
            navigation = backStack::push
          )
        }
      }

      StreaksTarget -> {
        node(buildContext) {
          val viewModel: AppViewModel = mavericksActivityViewModel()
          val state by viewModel.collectAsState { it.streakState }
          Streaks(state = state)
        }
      }

      SettingsTarget -> {
        node(buildContext) {
          val viewModel: AppViewModel = mavericksActivityViewModel()
          val state by viewModel.collectAsState { it.settingsState }
          Settings(state = state, actions = viewModel::send)
        }
      }
    }
  }
}

sealed class NavTarget : Parcelable {

  @Parcelize
  object HydrationTarget : NavTarget()

  @Parcelize
  object StreaksTarget : NavTarget()

  @Parcelize
  object SettingsTarget : NavTarget()

  @Parcelize
  object OnboardingTarget : NavTarget()
}


