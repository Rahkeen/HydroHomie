package com.rikin.hydrohomie.app.platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.rikin.hydrohomie.app.mavericks.suface.MavericksApp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      MavericksApp()
//      WorkflowApp()
    }
  }
}
