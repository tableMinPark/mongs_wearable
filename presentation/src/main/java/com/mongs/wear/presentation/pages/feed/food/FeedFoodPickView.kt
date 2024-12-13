package com.mongs.wear.presentation.pages.feed.food

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
import com.mongs.wear.domain.vo.FoodVo
import com.mongs.wear.presentation.global.component.background.FeedNestedBackground
import com.mongs.wear.presentation.global.component.button.BlueButton
import com.mongs.wear.presentation.global.component.button.LeftButton
import com.mongs.wear.presentation.global.component.button.RightButton
import com.mongs.wear.presentation.global.component.common.LoadingBar
import com.mongs.wear.presentation.global.component.common.PageIndicator
import com.mongs.wear.presentation.global.component.common.PayPoint
import com.mongs.wear.presentation.global.dialog.common.ConfirmDialog
import com.mongs.wear.presentation.global.dialog.feed.FeedItemDetailDialog
import com.mongs.wear.presentation.assets.FeedResourceCode
import com.mongs.wear.presentation.assets.NavItem
import com.mongs.wear.presentation.assets.DAL_MU_RI
import com.mongs.wear.presentation.assets.MongsWhite
import kotlin.math.max
import kotlin.math.min

@Composable
fun FeedFoodPickView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    feedFoodPickViewModel: FeedFoodPickViewModel = hiltViewModel(),
) {
    val foodVoIndex = remember { mutableIntStateOf(0) }
    val foodVoList = feedFoodPickViewModel.foodVoList.observeAsState(ArrayList())
    val payPoint = feedFoodPickViewModel.payPoint.observeAsState(0)
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = foodVoIndex.intValue
            override val pageCount: Int
                get() = foodVoList.value.size
        }
    }

    Box {
        if (feedFoodPickViewModel.uiState.loadingBar) {
            FeedNestedBackground()
            FeedFoodPickLoadingBar()
        } else if (feedFoodPickViewModel.uiState.buyDialog) {
            ConfirmDialog(
                text = "구매하시겠습니까?",
                confirm = {
                    feedFoodPickViewModel.buyFood(foodVoList.value[foodVoIndex.intValue].code)
                },
                cancel = {
                    feedFoodPickViewModel.uiState.buyDialog = false
                }
            )
        } else {
            FeedNestedBackground()
            PageIndicator(
                pageIndicatorState = pageIndicatorState,
                modifier = Modifier.zIndex(1f)
            )
            FeedFoodPickContent(
                payPoint = payPoint.value,
                foodVo = foodVoList.value[foodVoIndex.intValue],
                detailDialog = { feedFoodPickViewModel.uiState.detailDialog = true },
                buyDialog = { feedFoodPickViewModel.uiState.buyDialog = true },
                preFood = { foodVoIndex.intValue = max(foodVoIndex.intValue - 1, 0) },
                nextFood = {
                    foodVoIndex.intValue = min(foodVoIndex.intValue + 1, foodVoList.value.size - 1)
                },
                modifier = Modifier.zIndex(2f),
            )

            if (feedFoodPickViewModel.uiState.detailDialog) {
                FeedItemDetailDialog(
                    onClick = { feedFoodPickViewModel.uiState.detailDialog = false },
                    addWeight = foodVoList.value[foodVoIndex.intValue].addWeight,
                    addStrength = foodVoList.value[foodVoIndex.intValue].addStrength,
                    addSatiety = foodVoList.value[foodVoIndex.intValue].addSatiety,
                    addHealthy = foodVoList.value[foodVoIndex.intValue].addHealthy,
                    addSleep = foodVoList.value[foodVoIndex.intValue].addSleep,
                    modifier = Modifier.zIndex(3f)
                )
            }
        }

        LaunchedEffect(feedFoodPickViewModel.uiState.navMainPager) {
            if (feedFoodPickViewModel.uiState.navMainPager) {
                scrollPage(2)
                navController.popBackStack(route = NavItem.FeedNested.route, inclusive = true)
                feedFoodPickViewModel.uiState.navMainPager = false
            }
        }
        LaunchedEffect(feedFoodPickViewModel.uiState.navFeedMenu) {
            if (feedFoodPickViewModel.uiState.navFeedMenu) {
                navController.popBackStack()
                feedFoodPickViewModel.uiState.navFeedMenu = false
            }
        }
    }
}

@Composable
private fun FeedFoodPickLoadingBar(
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
private fun FeedFoodPickContent(
    payPoint: Int,
    foodVo: FoodVo,
    detailDialog: () -> Unit,
    buyDialog: () -> Unit,
    preFood: () -> Unit,
    nextFood: () -> Unit,
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
                verticalAlignment = Alignment.CenterVertically,
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
                        onClick = preFood,
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
                            text = foodVo.name,
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
                            painter = painterResource(FeedResourceCode.valueOf(foodVo.code).code),
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
                        onClick = nextFood,
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
                    text = "$${foodVo.price}",
                    width = 70,
                    disable = !foodVo.isCanBuy || foodVo.price > payPoint,
                    onClick = buyDialog,
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedSnackPickViewPreview() {
    Box {
        FeedNestedBackground()
        PageIndicator(
            pageIndicatorState = remember {
                object : PageIndicatorState {
                    override val pageOffset: Float
                        get() = 0f
                    override val selectedPage: Int
                        get() = 0
                    override val pageCount: Int
                        get() = 5
                }
            },
            modifier = Modifier.zIndex(1f)
        )
        FeedFoodPickContent(
            payPoint = 0,
            foodVo = FoodVo(name = "별사탕", isCanBuy = true),
            detailDialog = {},
            buyDialog = {},
            preFood = {},
            nextFood = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedSnackPickViewPreview() {
    Box {
        FeedNestedBackground()
        PageIndicator(
            pageIndicatorState = remember {
                object : PageIndicatorState {
                    override val pageOffset: Float
                        get() = 0f
                    override val selectedPage: Int
                        get() = 0
                    override val pageCount: Int
                        get() = 5
                }
            },
            modifier = Modifier.zIndex(1f)
        )
        FeedFoodPickContent(
            payPoint = 0,
            foodVo = FoodVo(name = "별사탕", isCanBuy = true),
            detailDialog = {},
            buyDialog = {},
            preFood = {},
            nextFood = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}

