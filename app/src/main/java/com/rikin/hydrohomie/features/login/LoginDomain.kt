package com.rikin.hydrohomie.features.login

import androidx.compose.runtime.staticCompositionLocalOf
import com.google.android.gms.auth.api.signin.GoogleSignInClient

val LocalGoogleClient = staticCompositionLocalOf<GoogleSignInClient> {
  noDefault()
}

private fun noDefault(): Nothing {
  error("GoogleSignInClient not provided")
}