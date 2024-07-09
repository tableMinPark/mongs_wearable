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
import androidx.compose.runtime.LaunchedEffect
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
import com.mongs.wear.ui.global.component.button.CircleImageButton
import com.mongs.wear.ui.global.component.button.CircleTextButton
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.dialog.common.ConfirmDialog
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.viewModel.mainConfigure.MainConfigureViewModel

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
                text = "걸음수가 초기화 됩니다.\n로그아웃하시겠습니까?",
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
                    navController.navigate(NavItem.PaymentNested.route)
                },
                help = {
                    navController.navigate(NavItem.HelpNested.route)
                },
                feedback = {
                    navController.navigate(NavItem.Feedback.route)
                },
                logout = {
                    mainConfigureViewModel.uiState.logoutDialog = true
                },
                modifier = Modifier.zIndex(1f)
            )
        }
    }

    LaunchedEffect(mainConfigureViewModel.uiState.navLoginView) {
        if (mainConfigureViewModel.uiState.navLoginView) {
            scrollPage(2)
            navController.navigate(NavItem.Login.route) {
                popUpTo(
                    navController.graph.id
                )
            }
            mainConfigureViewModel.uiState.navLoginView = false
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
    help: () -> Unit,
    feedback: () -> Unit,
    logout: () -> Unit,
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
                CircleImageButton(
                    icon = R.drawable.point_store,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = payment,
                )

                Spacer(modifier = Modifier.width(12.dp))

                CircleTextButton(
                    text = "i",
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = help,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleImageButton(
                    icon = R.drawable.feedback,
                    border = R.drawable.interaction_bnt_darkpurple,
                    onClick = feedback,
                )

                Spacer(modifier = Modifier.width(12.dp))

                CircleImageButton(
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
            help = {},
            feedback = {},
            logout = {},
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
            help = {},
            feedback = {},
            logout = {},
            modifier = Modifier.zIndex(1f)
        )
    }
}