package com.paymong.wear.ui.view_.initLanding

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.viewModel_.initLanding.InitLandingViewModel
import com.paymong.wear.domain.processCode.InitLandingProcessCode
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.googleSign.GoogleSignIn
import com.paymong.wear.ui.googleSign.GoogleSignInCheck
import com.paymong.wear.ui.view_.common.InitLandingBackground
import com.paymong.wear.ui.view_.common.LoadingBar
import com.paymong.wear.ui.view_.common.Logo

@Composable
fun InitLandingView(
    navController: NavController,
    initLandingViewModel: InitLandingViewModel = hiltViewModel()
) {
    /** Process Code **/
    val processCode = initLandingViewModel.processCode.observeAsState(InitLandingProcessCode.STAND_BY)
    /** Animation **/
    val logoSize = remember{ mutableIntStateOf(100) } // 140
    val googleSignInWidth =  remember{ mutableIntStateOf(170) } // 0
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(false) }
    val isSignInShow = remember{ mutableStateOf(false) }

    when (processCode.value) {
        InitLandingProcessCode.LOGIN -> {
            isLoadingBarShow.value = true
            isSignInShow.value = false
            GoogleSignIn(initLandingViewModel::mongLifeLogin, initLandingViewModel::googleSignInFail)
        }
        InitLandingProcessCode.GOOGLE_SIGN_IN_CHECK -> {
            isLoadingBarShow.value = true
            isSignInShow.value = false
            GoogleSignInCheck(initLandingViewModel::mongLifeLogin, initLandingViewModel::googleSignInFail)
        }
        InitLandingProcessCode.GOOGLE_SIGN_IN_FAIL -> {
            isLoadingBarShow.value = false
            isSignInShow.value = true
            Toast.makeText(
                LocalContext.current,
                InitLandingProcessCode.GOOGLE_SIGN_IN_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        InitLandingProcessCode.MONG_LIFE_LOGIN_FAIL -> {
            isLoadingBarShow.value = false
            isSignInShow.value = true
            Toast.makeText(
                LocalContext.current,
                InitLandingProcessCode.MONG_LIFE_LOGIN_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        InitLandingProcessCode.LOGIN_SUCCESS -> {
            isLoadingBarShow.value = false
            isSignInShow.value = false
            logoSize.intValue = 140
            googleSignInWidth.intValue = 0
        }
        InitLandingProcessCode.NAV_MAIN -> {
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
            googleSignIn = initLandingViewModel::googleSignIn,
            logoSize = logoSize.intValue,
            googleSignInWidth = googleSignInWidth.intValue,
            isLoadingBarShow = isLoadingBarShow.value,
            isSignInShow = isSignInShow.value
        )
    }

    LaunchedEffect(Unit) {
        initLandingViewModel.googleSignInCheck()
    }
}

@Composable
fun InitLandingContent(
    googleSignIn: () -> Unit,
    logoSize: Int,
    googleSignInWidth: Int,
    isLoadingBarShow: Boolean,
    isSignInShow: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                /** Logo **/
                Logo(size = logoSize)
            }
            Row(
                modifier = Modifier.padding(top = 15.dp)
            ) {
                if (isSignInShow) {
                    /** Google Login Button **/
                    SignIn(width = googleSignInWidth, onClick = googleSignIn)
                } else if (isLoadingBarShow) {
                    /** Loading Bar **/
                    LoadingBar(size = 30)
                }
            }
        }
    }
}
