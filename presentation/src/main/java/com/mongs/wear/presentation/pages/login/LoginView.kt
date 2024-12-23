package com.mongs.wear.presentation.pages.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.assets.DAL_MU_RI
import com.mongs.wear.presentation.assets.MongsWhite
import com.mongs.wear.presentation.assets.NavItem
import com.mongs.wear.presentation.component.background.LoginBackground
import com.mongs.wear.presentation.component.button.BlueButton
import com.mongs.wear.presentation.component.button.GoogleSignInButton
import com.mongs.wear.presentation.component.common.LoadingBar
import com.mongs.wear.presentation.component.common.Logo
import com.mongs.wear.presentation.pages.login.LoginViewModel.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    closeApp: () -> Unit,
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {

    DisposableEffect(Unit) {
        val window = (context as ComponentActivity).window
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    Box {
        LoginBackground()

        if (loginViewModel.uiState.needAppUpdate) {

            NeedUpdateAppContent(
                closeApp = closeApp,
                modifier = Modifier.zIndex(1f)
            )

        } else {

            LoginContent(
                login = loginViewModel::login,
                join = loginViewModel::join,
                uiState = loginViewModel.uiState,
                modifier = Modifier.zIndex(1f)
            )
        }
    }

    LaunchedEffect(loginViewModel.uiState.navMainPagerView) {
        if (loginViewModel.uiState.navMainPagerView) {
            navController.navigate(NavItem.MainPager.route) {
                popUpTo(navController.graph.id)
            }
            loginViewModel.uiState.navMainPagerView = false
        }
    }
}


@Composable
private fun LoginContent(
    login: suspend (googleAccountId: String?, email: String?) -> Unit,
    join: suspend (googleAccountId: String?, email: String?, name: String?) -> Unit,
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    modifier: Modifier = Modifier.zIndex(0f),
    uiState: UiState,
) {

    /**
     * 구글 회원 가입
     */
    LaunchedEffect(uiState.needJoin) {
        if (uiState.needJoin) {
            GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
                join(account.id, account.email, account.displayName)
            }
        }
    }

    /**
     * 구글 로그인 확인
     */
    LaunchedEffect(Unit) {

        uiState.loadingBar = true
        uiState.signInButton = false

        GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
            login(account.id, account.email)
            return@LaunchedEffect
        }

        uiState.loadingBar = false
        uiState.signInButton = true
    }

    /**
     * 구글 로그인
     */
    val googleLoginLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        coroutineScope.launch {
            if (result.resultCode == Activity.RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(result.data).result?.let { account ->
                    login(account.id, account.email)
                    return@launch
                }
            }

            uiState.loadingBar = false
            uiState.signInButton = true
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

                            val googleSignIn = GoogleSignIn.getLastSignedInAccount(context)
                            val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .requestId()
                                .build()

                            val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOption)

                            if (googleSignIn != null) {
                                googleSignInClient.signOut()
                            }

                            googleLoginLauncher.launch(googleSignInClient.signInIntent)
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


@SuppressLint("QueryPermissionsNeeded")
@Composable
private fun NeedUpdateAppContent (
    closeApp: () -> Unit,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Image(
                painter = painterResource(R.drawable.logo_not_open),
                contentDescription = null,
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "새로운 업데이트가 있습니다.\n앱을 업데이트 해주세요.",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = MongsWhite,
            )

            Spacer(modifier = Modifier.height(25.dp))

            val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=${context.packageName}")
                setPackage("com.android.vending")
            }

            if (playStoreIntent.resolveActivity(context.packageManager) != null) {
                BlueButton(
                    width = 80,
                    text =  "업데이트",
                    onClick = { context.startActivity(playStoreIntent) },
                )
            } else {
                BlueButton(
                    text =  "종료",
                    onClick = closeApp,
                )
            }
        }
    }
}