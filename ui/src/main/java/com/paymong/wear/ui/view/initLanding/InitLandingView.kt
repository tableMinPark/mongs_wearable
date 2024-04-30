package com.paymong.wear.ui.view.initLanding

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.viewModel.initLanding.InitLandingViewModel
import com.paymong.wear.domain.processCode.InitLandingProcessCode
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.googleSign.GoogleSignIn
import com.paymong.wear.ui.global.googleSign.GoogleSignInCheck
import com.paymong.wear.ui.global.component.InitLandingBackground

@Composable
fun InitLandingView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    initLandingViewModel: InitLandingViewModel = hiltViewModel()
) {
    /** Process Code **/
    val processCode = initLandingViewModel.processCode.observeAsState(InitLandingProcessCode.STAND_BY)
    /** Animation **/
    val logoSize = remember{ mutableIntStateOf(100) }
    val googleSignInWidth =  remember{ mutableIntStateOf(210) }
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(false) }
    val isSignInShow = remember{ mutableStateOf(false) }

    when (processCode.value) {
        InitLandingProcessCode.LOGIN -> {
            isLoadingBarShow.value = true
            isSignInShow.value = false
            GoogleSignIn(initLandingViewModel::mongsLogin, initLandingViewModel::googleSignInFail)
        }
        InitLandingProcessCode.GOOGLE_SIGN_IN_CHECK -> {
            isLoadingBarShow.value = true
            isSignInShow.value = false
            GoogleSignInCheck(initLandingViewModel::mongsLogin, initLandingViewModel::googleSignInFail)
        }
        InitLandingProcessCode.GOOGLE_SIGN_IN_FAIL -> {
            isLoadingBarShow.value = false
            isSignInShow.value = true
            logoSize.intValue = 65
            googleSignInWidth.intValue = 210
            Toast.makeText(
                LocalContext.current,
                InitLandingProcessCode.GOOGLE_SIGN_IN_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            initLandingViewModel.resetProcessCode()
        }
        InitLandingProcessCode.MONGS_LOGIN_FAIL -> {
            isLoadingBarShow.value = false
            isSignInShow.value = true
            logoSize.intValue = 65
            googleSignInWidth.intValue = 210
            Toast.makeText(
                LocalContext.current,
                InitLandingProcessCode.MONGS_LOGIN_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            initLandingViewModel.resetProcessCode()
        }
        InitLandingProcessCode.MUST_UPDATE_APP -> {
            isLoadingBarShow.value = false
            isSignInShow.value = true
            logoSize.intValue = 65
            googleSignInWidth.intValue = 210
            Toast.makeText(
                LocalContext.current,
                InitLandingProcessCode.MUST_UPDATE_APP.message,
                Toast.LENGTH_SHORT
            ).show()
            initLandingViewModel.resetProcessCode()
        }
        InitLandingProcessCode.LOGIN_SUCCESS -> {
            isLoadingBarShow.value = false
            isSignInShow.value = false
            logoSize.intValue = 130
            googleSignInWidth.intValue = 0
        }
        InitLandingProcessCode.NAV_MAIN -> {
            scrollPage(1)
            navController.navigate(NavItem.Main.route) {
                popUpTo(
                    navController.graph.id
                )
            }
        }
        else -> {}
    }

    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            InitLandingBackground()
        }

        InitLandingContent(
            logoSize = logoSize,
            googleSignInWidth = googleSignInWidth,
            isLoadingBarShow = isLoadingBarShow,
            isSignInShow = isSignInShow,
            googleSignIn = initLandingViewModel::googleSignIn,
        )
    }

    LaunchedEffect(Unit) {
        initLandingViewModel.googleSignInCheck()
    }
}
