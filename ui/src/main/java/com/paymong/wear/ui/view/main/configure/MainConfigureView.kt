package com.paymong.wear.ui.view.main.configure

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.processCode.MainConfigureProcessCode
import com.paymong.wear.ui.viewModel.main.MainConfigureViewModel
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.googleSign.GoogleSignOut

@Composable
fun MainConfigureView(
    scrollPage: (Int) -> Unit,
    navController: NavController,
    mainConfigureViewModel: MainConfigureViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    /** Process Code **/
    val processCode = mainConfigureViewModel.processCode.observeAsState(MainConfigureProcessCode.STAND_BY)
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(false) }

    when (processCode.value) {
        MainConfigureProcessCode.LOGOUT -> {
            isLoadingBarShow.value = true
            GoogleSignOut(mainConfigureViewModel::mongsLogout, mainConfigureViewModel::googleSignOutFail)
        }
        MainConfigureProcessCode.GOOGLE_SIGN_OUT_FAIL -> {
            isLoadingBarShow.value = false
            Toast.makeText(
                LocalContext.current,
                MainConfigureProcessCode.GOOGLE_SIGN_OUT_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            mainConfigureViewModel.resetProcessCode()
        }
        MainConfigureProcessCode.MONGS_LOGOUT_FAIL -> {
            isLoadingBarShow.value = false
            Toast.makeText(
                LocalContext.current,
                MainConfigureProcessCode.MONGS_LOGOUT_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            mainConfigureViewModel.resetProcessCode()
        }
        MainConfigureProcessCode.LOGOUT_SUCCESS -> {
            isLoadingBarShow.value = false
        }
        MainConfigureProcessCode.NAV_INIT_LANDING -> {
            scrollPage(1)
            navController.navigate(NavItem.InitLanding.route) {
                popUpTo(
                    navController.graph.id
                )
            }
        }
        else -> {}
    }

    Box {
        MainConfigureViewContent(
            signOut = { mainConfigureViewModel.googleSignOut() },
            mapSearch = { /* navController.navigate(NavItem.Reference.route) */
                Toast.makeText(
                    context,
                    "준비중입니다",
                    Toast.LENGTH_SHORT
                ).show()
            },
            charge = { // navController.navigate(NavItem.ChargeNested.route)
                Toast.makeText(
                    context,
                    "준비중입니다",
                    Toast.LENGTH_SHORT
                ).show()
            },
            feedback = { navController.navigate(NavItem.Feedback.route) },
        )
    }
}
