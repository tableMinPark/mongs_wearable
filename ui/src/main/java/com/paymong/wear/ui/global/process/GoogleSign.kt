package com.paymong.wear.ui.global.process

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.delay

@Composable
fun GoogleSignInCheck(
    success: (email: String?, name: String?) -> Unit,
    fail: () -> Unit,
    context: Context = LocalContext.current
) {
    LaunchedEffect(Unit) {
    }
}

@Composable
fun GoogleSignIn(
    success: (email: String?, userName: String?) -> Unit,
    fail: () -> Unit,
    context: Context = LocalContext.current
) {

    LaunchedEffect(Unit) {
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
