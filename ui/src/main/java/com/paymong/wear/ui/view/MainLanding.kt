package com.paymong.wear.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.wear.domain.viewModel.MainLandingCode
import com.paymong.wear.domain.viewModel.MainLandingViewModel
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view.common.LandingBackground
import com.paymong.wear.ui.view.common.FailOrError
import com.paymong.wear.ui.view.common.Logo

@Composable
fun MainLanding(
    navController: NavController,
    mainLandingViewModel: MainLandingViewModel = hiltViewModel()
) {
    LaunchedEffect(NavItem.MainLanding.route) {
        // 로그인 함수 호출
        mainLandingViewModel.login()
    }
    LandingBackground()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            when(mainLandingViewModel.processCode) {
                MainLandingCode.START -> Logo()
                MainLandingCode.SUCCESS -> navController.navigate(NavItem.Main.route){ popUpTo(navController.graph.id) }
                else -> FailOrError(mainLandingViewModel.processCode.message)
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun MainLandingPreView() {
    MainLanding(rememberNavController())
}
