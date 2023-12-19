package com.paymong.wear.ui.view.main.configure

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.PositionIndicator
import com.paymong.wear.domain.viewModel.code.ConfigureCode
import com.paymong.wear.domain.viewModel.main.ConfigureViewModel
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view.common.auth.GoogleSignOut
import com.paymong.wear.ui.view.common.background.Process

@Composable
fun ConfigureView(
    navController: NavController,
    configureViewModel: ConfigureViewModel = hiltViewModel()
) {
    /** Data **/
    val processCode = configureViewModel.processCode.observeAsState(ConfigureCode.STAND_BY)

    Log.d("test", processCode.value.toString())

    /** Logic by ProcessCode **/
    when(processCode.value) {
        ConfigureCode.SIGN_OUT_PROCESS -> {
            GoogleSignOut(configureViewModel::googleSignOutSuccess, configureViewModel::googleSignOutFail)
        }
        ConfigureCode.SIGN_OUT_SUCCESS -> {
            configureViewModel.googleSignOutEnd()
        }
        ConfigureCode.SIGN_OUT_FAIL -> {
            Toast.makeText(
                LocalContext.current,
                processCode.value.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        ConfigureCode.SIGN_OUT_END -> {
            navController.navigate(NavItem.InitLanding.route) {
                popUpTo(
                    navController.graph.id
                )
            }
        }
        else -> {}
    }

    /** Content **/
    Box {
        ConfigureContent(
            googleSingOut = configureViewModel::googleSignOutProcess,
            navSetting = {
                navController.navigate(NavItem.Setting.route)
            },
            navFeedback = {
                navController.navigate(NavItem.Feedback.route)
            },
            navDeveloper = {
                navController.navigate(NavItem.Developer.route)
            }
        )
        if (processCode.value == ConfigureCode.SIGN_OUT_PROCESS) {
            SignOutLoadingContent()
        }
    }
}

@Composable
fun ConfigureContent(
    googleSingOut: () -> Unit,
    navSetting: () -> Unit,
    navFeedback: () -> Unit,
    navDeveloper: () -> Unit
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 1)

    Box (
        modifier = Modifier
            .fillMaxSize()
            .zIndex(0f),
        contentAlignment = Alignment.Center
    ) {
        PositionIndicator(scalingLazyListState = listState)
        ScalingLazyColumn (
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
            autoCentering = AutoCenteringParams(itemIndex = 1),
            state = listState
        ) {
            item {
                Icon(
                    imageVector = Icons.Outlined.SettingsApplications,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .size(48.dp)
                )
            }
            item {
                // TODO : 로그 아웃
                ConfigureButton(
                    icon = Icons.Outlined.Logout,
                    label= "로그아웃",
                    secondaryLabel = "Google 로그아웃",
                    onClick = googleSingOut
                )
            }
            item {
                // TODO : 설정 화면 이동
                ConfigureButton(
                    icon = Icons.Outlined.Settings,
                    label= "앱 설정",
                    secondaryLabel = "사운드",
                    onClick = navSetting
                )
            }
            item {
                // TODO : 피드백 화면 이동
                ConfigureButton(
                    icon = Icons.Outlined.Feedback,
                    label= "피드백",
                    secondaryLabel = "오류 신고, 요청 사항",
                    onClick = navFeedback
                )
            }
            item {
                // TODO : 개발자 화면 이동
                ConfigureButton(
                    icon = Icons.Outlined.Person,
                    label= "개발자 정보",
                    secondaryLabel = "개발자 정보",
                    onClick = navDeveloper
                )
            }
        }
    }
}

@Composable
fun SignOutLoadingContent() {
    Box (
        modifier = Modifier
            .zIndex(0f)
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Process(processSize = 30)
    }
}