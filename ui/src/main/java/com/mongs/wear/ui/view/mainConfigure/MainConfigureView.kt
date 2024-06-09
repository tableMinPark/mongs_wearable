package com.mongs.wear.ui.view.mainConfigure

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import com.mongs.wear.ui.global.component.button.CircleButton
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.dialog.ConfirmDialog
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.viewModel.mainConfigure.MainConfigureViewModel
import com.mongs.wear.ui.viewModel.mainConfigure.MainConfigureViewModel.UiState

@Composable
fun MainConfigureView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    mainConfigureViewModel: MainConfigureViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    Box {
        if (mainConfigureViewModel.uiState.loadingBar) {
            MainConfigureLoadingBar(modifier = Modifier.zIndex(1f))
        } else if (mainConfigureViewModel.uiState.logoutDialog) {
            ConfirmDialog(
                text = "로그아웃\n하시겠습니까?",
                confirm = {
                    mainConfigureViewModel.uiState.loadingBar = true
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .build()
                    val client = GoogleSignIn.getClient(context, gso)
                    client.signOut()
                        .addOnCompleteListener {
                            mainConfigureViewModel.logout()
                        }.addOnFailureListener {
                            Toast.makeText(context, "잠시후 다시 시도", Toast.LENGTH_SHORT).show()
                        }
                },
                cancel = {
                    mainConfigureViewModel.uiState.logoutDialog = false
                }
            )
        } else {
            MainConfigureContent(
                payment = {
                    Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
//                    navController.navigate(NavItem.PaymentNested.route)
                },
                mapSearch = {
                    Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
                },
                feedback = {
                    Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
//                    navController.navigate(NavItem.Feedback.route)
                },
                logout = {
                    mainConfigureViewModel.uiState.logoutDialog = true
                },
                uiState = mainConfigureViewModel.uiState,
                modifier = Modifier.zIndex(1f)
            )
        }
    }

    if (mainConfigureViewModel.uiState.navLoginView) {
        scrollPage(1)
        navController.navigate(NavItem.Login.route) {
            popUpTo(
                navController.graph.id
            )
        }
    }
}

@Composable
private fun MainConfigureLoadingBar(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        LoadingBar()
    }
}

@Composable
private fun MainConfigureContent(
    payment: () -> Unit,
    mapSearch: () -> Unit,
    feedback: () -> Unit,
    logout: () -> Unit,
    uiState: UiState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleButton(
                    icon = R.drawable.charge_startpoint,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = payment,
                )

                Spacer(modifier = Modifier.width(12.dp))

                CircleButton(
                    icon = R.drawable.map_search,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = mapSearch,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleButton(
                    icon = R.drawable.feedback,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = feedback,
                )

                Spacer(modifier = Modifier.width(12.dp))

                CircleButton(
                    icon = R.drawable.logout,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = logout,
                )
            }
        }
    }
}


@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainConfigureViewPreView() {
    Box {
        MainPagerBackground()
        MainConfigureContent(
            payment = {},
            mapSearch = {},
            feedback = {},
            logout = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(1f)
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeMainConfigureViewPreView() {
    Box {
        MainPagerBackground()
        MainConfigureContent(
            payment = {},
            mapSearch = {},
            feedback = {},
            logout = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(1f)
        )
    }
}