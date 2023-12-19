package com.paymong.wear.ui.view.initLanding

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.viewModel.code.InitLandingCode
import com.paymong.wear.domain.viewModel.initLanding.InitLandingViewModel
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view.common.auth.GoogleSignIn
import com.paymong.wear.ui.view.common.auth.GoogleSignInCheck
import com.paymong.wear.ui.view.common.background.LandingBackground
import com.paymong.wear.ui.view.common.background.Process

const val landingLogoSize = 120
const val processLogoSize = 100
const val loginLogoSize = 80
const val processSize = 30
const val loginSize = 170

@Composable
fun MainLandingView(
    navController: NavController,
    initLandingViewModel: InitLandingViewModel = hiltViewModel()
) {
    /** Data **/
    val processCode = initLandingViewModel.processCode.observeAsState(InitLandingCode.STAND_BY)

    /** Animation **/
    val logoSize = animateIntAsState(
        targetValue = when (processCode.value) {
            InitLandingCode.SIGN_IN_CHECK -> processLogoSize
            InitLandingCode.SIGN_IN_INIT -> loginLogoSize
            InitLandingCode.SIGN_IN_PROCESS -> processLogoSize
            else -> landingLogoSize
        },
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        ),
        label = "logoSize"
    )
    val loginSize = animateIntAsState(
        targetValue = when (processCode.value) {
            InitLandingCode.SIGN_IN_INIT -> loginSize
            else -> 0
        },
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 0
        ),
        label = "loginSize"
    )
    val processSize = animateIntAsState(
        targetValue = when (processCode.value) {
            InitLandingCode.SIGN_IN_CHECK -> processSize
            InitLandingCode.SIGN_IN_PROCESS -> processSize
            InitLandingCode.SIGN_IN_SUCCESS -> processSize
            else -> 0
        },
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 0
        ),
        label = "processSize"
    )

    /** LaunchedEffect & Background **/
    LaunchedEffect(Unit) {
        // 로그인 여부 확인
        initLandingViewModel.googleSignInCheck()
    }
    LandingBackground()

    /** Logic by ProcessCode **/
    when(processCode.value){
        InitLandingCode.SIGN_IN_CHECK -> {
            GoogleSignInCheck(initLandingViewModel::googleSignInAlready, initLandingViewModel::googleSignInInit)
        }
        InitLandingCode.SIGN_IN_PROCESS_GOOGLE -> {
            GoogleSignIn(initLandingViewModel::googleSignInSuccess, initLandingViewModel::googleSignInFail)
        }
        InitLandingCode.SIGN_IN_PROCESS -> {

        }
        InitLandingCode.END -> {
            navController.navigate(NavItem.Main.route) {
                popUpTo(
                    navController.graph.id
                )
            }
        }
        else -> {}
    }

    /** Content **/
    MainLandingContent(
        initLandingViewModel = initLandingViewModel, processCode = processCode,
        logoSize = logoSize, processSize = processSize, loginSize = loginSize
    )
}

@Composable
fun MainLandingContent(
    processCode: State<InitLandingCode>,
    initLandingViewModel: InitLandingViewModel,
    logoSize: State<Int>,
    processSize: State<Int>,
    loginSize: State<Int>,
) {
    Box {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            ) {
                Logo(logoSize = logoSize.value)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                when (processCode.value) {
                    InitLandingCode.SIGN_IN_CHECK -> {
                        Process(processSize = processSize.value)
                    }
                    InitLandingCode.SIGN_IN_PROCESS -> {
                        Process(processSize = processSize.value)
                    }
                    InitLandingCode.SIGN_IN_INIT -> {
                        Login(
                            loginSize = loginSize.value,
                            initLandingViewModel::googleSignInProcess
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}