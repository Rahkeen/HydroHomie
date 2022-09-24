package com.rikin.hydrohomie.features.login

import android.app.Activity
import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.tasks.Task
import com.rikin.hydrohomie.design.HydroHomieTheme
import com.rikin.hydrohomie.design.PopRed
import com.rikin.hydrohomie.design.WispyWhite

@Composable
fun Login() {
  val startForResult =
    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
      if (result.resultCode == RESULT_OK) {
        val intent = result.data
        if (result.data != null) {
          val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        }
      }
    }

  val intent = LocalGoogleClient.current.signInIntent

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background),
    contentAlignment = Alignment.Center
  ) {
    Button(
      onClick = { startForResult.launch(intent) },
      modifier = Modifier.wrapContentSize(),
      elevation = null,
      shape = CircleShape,
      colors = ButtonDefaults.buttonColors(
        backgroundColor = PopRed,
        contentColor = WispyWhite
      )
    ) {
      Text(text = "Login with Google")
    }
  }
}

@Preview
@Composable
fun LoginPreview() {
  HydroHomieTheme {
    Login()
  }
}