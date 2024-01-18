package com.paymong.wear.ui.googleSign

import android.app.Activity
import android.content.Context
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
    success: (userName: String, email: String) -> Unit,
    fail: () -> Unit,
    context: Context = LocalContext.current
) {
    LaunchedEffect(Unit) {
        delay(800)
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            val userName = account.givenName!!
            val email = account.email!!
            success(userName, email)
        } else {
            fail()
        }
    }
}

@Composable
fun GoogleSignIn(
    success: (userName: String?, email: String?) -> Unit,
    fail: () -> Unit,
    context: Context = LocalContext.current
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

    LaunchedEffect(Unit) {
        delay(800)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context, gso)
        val signInIntent = client.signInIntent
        resultLauncher.launch(signInIntent)
    }
}

@Composable
fun GoogleSignOut(
    success: () -> Unit,
    fail: () -> Unit,
    context: Context = LocalContext.current
) {
    LaunchedEffect(Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()
        val client = GoogleSignIn.getClient(context, gso)
        client.signOut()
            .addOnCompleteListener {
                success()
            }.addOnFailureListener {
                fail()
            }
    }
}
