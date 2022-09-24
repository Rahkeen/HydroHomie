package com.rikin.hydrohomie.app.platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.rikin.hydrohomie.R
import com.rikin.hydrohomie.app.mavericks.suface.MavericksApp
import com.rikin.hydrohomie.features.login.LocalGoogleClient

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      CompositionLocalProvider(LocalGoogleClient provides getGoogleLoginAuth()) {
        MavericksApp()
      }
    }
  }

  private fun getGoogleLoginAuth(): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(DEFAULT_SIGN_IN)
      .requestEmail()
      .requestIdToken(OAUTH_ID)
      .requestId()
      .requestProfile()
      .build()

    return GoogleSignIn.getClient(this, gso)
  }
}

private const val OAUTH_ID = "797341136272-pgccmk1adtm06hi65hnnndi2elj623h0.apps.googleusercontent.com"
