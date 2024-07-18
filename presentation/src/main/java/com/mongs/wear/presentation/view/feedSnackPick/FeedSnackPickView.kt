package com.mongs.wear.presentation.view.feedSnackPick

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.Text
import com.mongs.wear.domain.vo.SnackVo
import com.mongs.wear.presentation.global.component.background.FeedNestedBackground
import com.mongs.wear.presentation.global.component.button.BlueButton
import com.mongs.wear.presentation.global.component.button.LeftButton
import com.mongs.wear.presentation.global.component.button.RightButton
import com.mongs.wear.presentation.global.component.common.LoadingBar
import com.mongs.wear.presentation.global.component.common.PageIndicator
import com.mongs.wear.presentation.global.component.common.PayPoint
import com.mongs.wear.presentation.global.dialog.common.ConfirmDialog
import com.mongs.wear.presentation.global.dialog.feed.FeedItemDetailDialog
import com.mongs.wear.presentation.global.resource.FeedResourceCode
import com.mongs.wear.presentation.global.resource.NavItem
import com.mongs.wear.presentation.global.theme.DAL_MU_RI
import com.mongs.wear.presentation.global.theme.MongsWhite
import com.mongs.wear.presentation.viewModel.feedSnackPick.FeedSnackPickViewModel
import kotlin.math.max
import kotlin.math.min

@Composable
fun FeedSnackPickView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    feedSnackPickViewModel: FeedSnackPickViewModel = hiltViewModel(),
) {
    val snackVoIndex = remember { mutableIntStateOf(0) }
    val snackVoList = feedSnackPickViewModel.snackVoList.observeAsState(ArrayList())
    val payPoint = feedSnackPickViewModel.payPoint.observeAsState(0)
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = snackVoIndex.intValue
            override val pageCount: Int
                get() = snackVoList.value.size
        }
    }

    Box {
        if (feedSnackPickViewModel.uiState.loadingBar) {
            FeedNestedBackground()
            FeedSnackPickLoadingBar()
        } else if(feedSnackPickViewModel.uiState.buyDialog) {
            ConfirmDialog(
                text = "구매하시겠습니까?",
                confirm = {
                    feedSnackPickViewModel.uiState.loadingBar = true
                    feedSnackPickViewModel.buySnack(snackCode = snackVoList.value[snackVoIndex.intValue].code)
                },
                cancel = {
                    feedSnackPickViewModel.uiState.buyDialog = false
                }
            )
        } else {
            FeedNestedBackground()
            PageIndicator(
                pageIndicatorState = pageIndicatorState,
                modifier = Modifier.zIndex(1f)
            )
            FeedSnackPickContent(
                payPoint = payPoint.value,
                snackVo = snackVoList.value[snackVoIndex.intValue],
                detailDialog = { feedSnackPickViewModel.uiState.detailDialog = true },
                buyDialog = { feedSnackPickViewModel.uiState.buyDialog = true },
                preSnack = { snackVoIndex.intValue = max(snackVoIndex.intValue - 1, 0) },
                nextSnack = { snackVoIndex.intValue = min(snackVoIndex.intValue + 1, snackVoList.value.size - 1) },
                modifier = Modifier.zIndex(2f),
            )

            if (feedSnackPickViewModel.uiState.detailDialog) {
                FeedItemDetailDialog(
                    onClick = { feedSnackPickViewModel.uiState.detailDialog = false },
                    addWeight = snackVoList.value[snackVoIndex.intValue].addWeight,
                    addStrength = snackVoList.value[snackVoIndex.intValue].addStrength,
                    addSatiety = snackVoList.value[snackVoIndex.intValue].addSatiety,
                    addHealthy = snackVoList.value[snackVoIndex.intValue].addHealthy,
                    addSleep = snackVoList.value[snackVoIndex.intValue].addSleep,
                    modifier = Modifier.zIndex(3f)
                )
            }
        }
    }

    LaunchedEffect(feedSnackPickViewModel.uiState.navMainPager) {
        if (feedSnackPickViewModel.uiState.navMainPager) {
            scrollPage(2)
            navController.popBackStack(route = NavItem.FeedNested.route, inclusive = true)
            feedSnackPickViewModel.uiState.navMainPager = false
        }
    }
    LaunchedEffect(feedSnackPickViewModel.uiState.navFeedMenu) {
        if (feedSnackPickViewModel.uiState.navFeedMenu) {
            navController.popBackStack()
            feedSnackPickViewModel.uiState.navFeedMenu = false
        }
    }
}

@Composable
private fun FeedSnackPickLoadingBar(
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
private fun FeedSnackPickContent(
    payPoint: Int,
    snackVo: SnackVo,
    detailDialog: () -> Unit,
    buyDialog: () -> Unit,
    preSnack: () -> Unit,
    nextSnack: () -> Unit,
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
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                ) {
                    LeftButton(
                        onClick = preSnack,
                    )

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.6f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = detailDialog,
                        ),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                    ) {
                        Text(
                            text = snackVo.name,
                            textAlign = TextAlign.Center,
                            fontFamily = DAL_MU_RI,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
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
                        Image(
                            painter = painterResource(FeedResourceCode.valueOf(snackVo.code).code),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                ) {
                    RightButton(
                        onClick = nextSnack,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f)
            ) {
                BlueButton(
                    text = "$${snackVo.price}",
                    width = 70,
                    disable = !snackVo.isCanBuy || snackVo.price > payPoint,
                    onClick = { if (snackVo.isCanBuy) buyDialog() },
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedSnackPickViewPreview() {
    Box {
        FeedNestedBackground()
        FeedSnackPickContent(
            payPoint = 0,
            snackVo = SnackVo(isCanBuy = true),
            detailDialog = {},
            buyDialog = {},
            preSnack = {},
            nextSnack = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedSnackPickViewPreview() {
    Box {
        FeedNestedBackground()
        FeedSnackPickContent(
            payPoint = 0,
            snackVo = SnackVo(isCanBuy = true),
            detailDialog = {},
            buyDialog = {},
            preSnack = {},
            nextSnack = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}

