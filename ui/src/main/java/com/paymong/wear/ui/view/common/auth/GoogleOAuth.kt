package com.paymong.wear.ui.view.common.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.delay

@Composable
fun GoogleSignInCheck(
    signInAlready: (userName: String, email: String) -> Unit,
    signInInit: () -> Unit
) {
    val account = GoogleSignIn.getLastSignedInAccount(LocalContext.current)

    LaunchedEffect(Unit) {
        delay(800)

        if (account != null) {
            val userName = account.givenName!!
            val email = account.email!!
            signInAlready(userName, email)
        } else {
            signInInit()
        }
    }
}

@Composable
fun GoogleSignIn(
    success: (userName: String?, email: String?) -> Unit,
    fail: () -> Unit
) {
    val resultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
            val userName = account.givenName
            val email = account.email
            success(userName, email)
        } else {
            fail()
        }
    }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    val client = GoogleSignIn.getClient(LocalContext.current, gso)
    val signInIntent = client.signInIntent

    LaunchedEffect(Unit) {
        resultLauncher.launch(signInIntent)
    }
}

@Composable
fun GoogleSignOut(
    success: () -> Unit,
    fail: () -> Unit
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .build()
    val client = GoogleSignIn.getClient(LocalContext.current, gso)

    LaunchedEffect(Unit) {
        client.signOut()
            .addOnCompleteListener {
                success()
            }.addOnFailureListener {
                fail()
            }
    }
}
