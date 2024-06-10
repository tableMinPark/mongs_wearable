package com.mongs.wear.ui.view.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mongs.wear.ui.global.component.background.LoginBackground
import com.mongs.wear.ui.global.component.button.GoogleSignInButton
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.component.common.Logo
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.viewModel.login.LoginViewModel
import com.mongs.wear.ui.viewModel.login.LoginViewModel.UiState
import kotlinx.coroutines.delay

@Composable
fun LoginView(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    Box {
        LoginBackground()
        LoginContent(
            loginSuccess = loginViewModel::loginSuccess,
            loginFail = loginViewModel::loginFail,
            uiState = loginViewModel.uiState,
            modifier = Modifier.zIndex(1f)
        )
    }

    if (loginViewModel.uiState.navMainPagerView) {
        navController.navigate(NavItem.MainPager.route) {
            popUpTo(navController.graph.id)
        }
    }
}


@Composable
private fun LoginContent(
    loginSuccess: (email: String?, name: String?) -> Unit,
    loginFail: () -> Unit,
    uiState: UiState = UiState(),
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    // 구글 로그인 확인
    LaunchedEffect(Unit) {
        uiState.loadingBar = true
        uiState.signInButton = false

        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            loginSuccess(account.email, account.givenName)
        } else {
            loginFail()
        }
    }

    // 구글 로그인
    val googleLoginLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
            loginSuccess(account.email, account.givenName)
        } else {
            loginFail()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.weight(0.6f)
            ) {
                Logo(isOpen = !uiState.signInButton)
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = if (uiState.signInButton) Alignment.Top else Alignment.CenterVertically,
                modifier = Modifier.weight(0.4f)
            ) {
                if (uiState.signInButton) {
                    GoogleSignInButton(
                        onClick = {
                            uiState.loadingBar = true
                            uiState.signInButton = false

                            val account = GoogleSignIn.getLastSignedInAccount(context)
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build()
                            val client = GoogleSignIn.getClient(context, gso)

                            if (account != null) {
                                client.signOut()
                            }

                            googleLoginLauncher.launch(client.signInIntent)
                        }
                    )
                }

                if (uiState.loadingBar) {
                    LoadingBar()
                }
            }
        }
    }
}