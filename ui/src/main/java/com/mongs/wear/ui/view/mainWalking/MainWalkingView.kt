package com.mongs.wear.ui.view.mainWalking

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Text
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.component.common.PayPoint
import com.mongs.wear.ui.global.dialog.ConfirmDialog
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite
import com.mongs.wear.ui.viewModel.mainWalking.MainWalkingViewModel
import com.mongs.wear.ui.viewModel.mainWalking.MainWalkingViewModel.UiState

@Composable
fun MainWalkingView(
    mainWalkingViewModel: MainWalkingViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    Box {
        if (mainWalkingViewModel.uiState.loadingBar) {
            MainWalkingLoadingBar()
        } else {
            val payPoint = mainWalkingViewModel.payPoint.observeAsState(0)
            val walkingCount = mainWalkingViewModel.walkingCount.observeAsState(0)
//            val chargePayPoint = 10 * (walkingCount.value / 100)
            val chargePayPoint = walkingCount.value

            if(mainWalkingViewModel.uiState.chargePayPointDialog) {
                ConfirmDialog(
                    text = "$$chargePayPoint\n환전하시겠습니까?",
                    confirm = {
                        mainWalkingViewModel.chargePayPoint(chargePayPoint = chargePayPoint)
                        Toast.makeText(context, "$chargePayPoint 포인트 환전", Toast.LENGTH_SHORT).show()
                    },
                    cancel = { mainWalkingViewModel.uiState.chargePayPointDialog = false }
                )
            } else {
                MainWalkingContent(
                    chargePayPoint = chargePayPoint,
                    payPoint = payPoint.value,
                    walkingCount = walkingCount.value,
                    uiState = mainWalkingViewModel.uiState,
                    modifier = Modifier.zIndex(1f)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        mainWalkingViewModel.loadData()
    }
}

@Composable
private fun MainWalkingLoadingBar(
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
private fun MainWalkingContent(
    chargePayPoint: Int,
    payPoint: Int,
    walkingCount: Int,
    uiState: UiState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                PayPoint(payPoint = payPoint)
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.55f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {},
                        ),
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                    ) {
                        Text(
                            text = "$walkingCount 걸음",
                            textAlign = TextAlign.Center,
                            fontFamily = DAL_MU_RI,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = PaymongWhite,
                            maxLines = 1,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.pointlogo),
                            contentDescription = null,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            contentScale = ContentScale.FillBounds,
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "+ $chargePayPoint",
                            textAlign = TextAlign.Center,
                            fontFamily = DAL_MU_RI,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            color = PaymongWhite,
                            maxLines = 1,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f)
            ) {
                BlueButton(
                    text = "환전",
                    width = 70,
                    disable = false,
                    onClick = { uiState.chargePayPointDialog = true },
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainWalkingViewPreview() {
    Box {
        MainPagerBackground()
        MainWalkingContent(
            chargePayPoint = 0,
            payPoint = 0,
            walkingCount = 200,
            uiState = UiState(),
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeMainWalkingViewPreview() {
    Box {
        MainPagerBackground()
        MainWalkingContent(
            chargePayPoint = 0,
            payPoint = 0,
            walkingCount = 100,
            uiState = UiState(),
            modifier = Modifier.zIndex(1f)
        )
    }
}