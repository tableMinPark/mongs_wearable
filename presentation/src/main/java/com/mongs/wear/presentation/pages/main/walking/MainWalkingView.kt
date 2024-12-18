package com.mongs.wear.presentation.pages.main.walking

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.slot.vo.SlotVo
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.assets.DAL_MU_RI
import com.mongs.wear.presentation.assets.MongsWhite
import com.mongs.wear.presentation.component.background.MainPagerBackground
import com.mongs.wear.presentation.component.button.BlueButton
import com.mongs.wear.presentation.component.button.LeftButton
import com.mongs.wear.presentation.component.button.RightButton
import com.mongs.wear.presentation.component.common.LoadingBar
import com.mongs.wear.presentation.component.common.PayPoint
import com.mongs.wear.presentation.component.dialog.common.ConfirmDialog
import com.mongs.wear.presentation.pages.main.walking.MainWalkingViewModel.UiState
import kotlin.math.max
import kotlin.math.min

@Composable
fun MainWalkingView(
    slotVo: SlotVo,
    mainWalkingViewModel: MainWalkingViewModel = hiltViewModel(),
) {
    Box {
        if (mainWalkingViewModel.uiState.loadingBar) {
            MainWalkingLoadingBar()
        } else {
            val payPoint = mainWalkingViewModel.payPoint.observeAsState(0)
            val walkingCountAll = mainWalkingViewModel.walkingCount.observeAsState(0)
            val ratio = remember {
                mutableIntStateOf(0)
            }
            val walkingCount = remember {
                derivedStateOf {
                    walkingCountAll.value - 100 * ratio.intValue
                }
            }
            val chargePayPoint = remember {
                derivedStateOf {
                    10 * ratio.intValue
                }
            }

            if(mainWalkingViewModel.uiState.chargePayPointDialog) {
                ConfirmDialog(
                    text = "$${chargePayPoint.value}\n환전하시겠습니까?",
                    confirm = {
                        mainWalkingViewModel.chargePayPoint(
                            mongId = slotVo.mongId,
                            walkingCount = 100 * ratio.intValue,
                        )
                    },
                    cancel = { mainWalkingViewModel.uiState.chargePayPointDialog = false }
                )
            } else {
                MainWalkingContent(
                    slotVo = slotVo,
                    chargePayPoint = chargePayPoint.value,
                    payPoint = payPoint.value,
                    walkingCount = walkingCount.value,
                    uiState = mainWalkingViewModel.uiState,
                    increaseRatio = { ratio.intValue = min(ratio.intValue + 1, walkingCountAll.value / 100) },
                    decreaseRatio = { ratio.intValue = max(ratio.intValue - 1, 0) },
                    modifier = Modifier.zIndex(1f)
                )
            }
        }
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
    slotVo: SlotVo,
    chargePayPoint: Int,
    payPoint: Int,
    walkingCount: Int,
    uiState: UiState,
    increaseRatio: () -> Unit,
    decreaseRatio: () -> Unit,
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
                            color = MongsWhite,
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
                        LeftButton(onClick = decreaseRatio)

                        Spacer(modifier = Modifier.width(30.dp))

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
                            color = MongsWhite,
                            maxLines = 1,
                        )

                        Spacer(modifier = Modifier.width(30.dp))

                        RightButton(onClick = increaseRatio)
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
                    disable = chargePayPoint == 0 || slotVo.shiftCode == ShiftCode.EMPTY,
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
            slotVo = SlotVo(),
            chargePayPoint = 200,
            payPoint = 0,
            walkingCount = 200,
            uiState = UiState(),
            increaseRatio = {},
            decreaseRatio = {},
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
            slotVo = SlotVo(),
            chargePayPoint = 100,
            payPoint = 0,
            walkingCount = 100,
            uiState = UiState(),
            increaseRatio = {},
            decreaseRatio = {},
            modifier = Modifier.zIndex(1f)
        )
    }
}