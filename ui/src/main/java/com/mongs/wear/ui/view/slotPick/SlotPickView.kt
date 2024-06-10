package com.mongs.wear.ui.view.slotPick

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import kotlin.math.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.Text
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.FeedNestedBackground
import com.mongs.wear.ui.global.component.background.SlotPickBackground
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.component.button.LeftButton
import com.mongs.wear.ui.global.component.button.RightButton
import com.mongs.wear.ui.global.component.button.YellowButton
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.component.common.Mong
import com.mongs.wear.ui.global.component.common.PageIndicator
import com.mongs.wear.ui.global.component.common.StarPoint
import com.mongs.wear.ui.global.dialog.ConfirmDialog
import com.mongs.wear.ui.global.dialog.SlotDetailDialog
import com.mongs.wear.ui.global.resource.MongResourceCode
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite
import com.mongs.wear.ui.viewModel.slotPick.SlotPickViewModel
import kotlin.math.min

@Composable
fun SlotPickView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    slotPickViewModel: SlotPickViewModel = hiltViewModel(),
) {
    val slotVoIndex = remember { mutableIntStateOf(0) }
    val starPoint = slotPickViewModel.starPoint.observeAsState(0)
    val buySlotPrice = slotPickViewModel.buySlotPrice.observeAsState(0)
    val maxSlot = slotPickViewModel.maxSlot.observeAsState(1)
    val slotVoList = slotPickViewModel.slotVoList.observeAsState(ArrayList())
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = slotVoIndex.intValue
            override val pageCount: Int
                get() = 3
        }
    }

    Box {
        val slotVo =
            if (slotVoIndex.intValue < slotVoList.value.size) slotVoList.value[slotVoIndex.intValue] else SlotVo()
        if (slotPickViewModel.uiState.loadingBar) {
            FeedNestedBackground()
            SlotPickLoadingBar()
        } else if (slotPickViewModel.uiState.addDialog) {
            ConfirmDialog(
                text = "새로운 몽을\n생성하시겠습니까?",
                confirm = {
                    slotPickViewModel.uiState.loadingBar = true
                    slotPickViewModel.addMong("테스트", "22:00", "08:00")
                },
                cancel = {
                    slotPickViewModel.uiState.addDialog = false
                }
            )
        } else if (slotPickViewModel.uiState.deleteDialog) {
            ConfirmDialog(
                text = "현재 몽을\n삭제하시겠습니까?",
                confirm = {
                    slotPickViewModel.uiState.loadingBar = true
                    slotPickViewModel.deleteMong(mongId = slotVo.mongId)
                },
                cancel = {
                    slotPickViewModel.uiState.deleteDialog = false
                }
            )
        } else if (slotPickViewModel.uiState.pickDialog) {
            ConfirmDialog(
                text = "현재 몽을\n선택하시겠습니까?",
                confirm = {
                    slotPickViewModel.uiState.loadingBar = true
                    slotPickViewModel.pickMong(mongId = slotVo.mongId)
                },
                cancel = {
                    slotPickViewModel.uiState.pickDialog = false
                }
            )
        } else if (slotPickViewModel.uiState.buySlotDialog) {
            ConfirmDialog(
                text = "새로운 슬롯을\n구매하시겠습니까?",
                confirm = {
                    slotPickViewModel.uiState.loadingBar = true
                    slotPickViewModel.buySlot()
                },
                cancel = {
                    slotPickViewModel.uiState.buySlotDialog = false
                }
            )
        } else if (slotPickViewModel.uiState.detailDialog) {
            SlotPickBackground()
            SlotDetailDialog(
                onClick = { slotPickViewModel.uiState.detailDialog = false },
                mongId = slotVo.mongId,
                name = slotVo.name,
                weight = slotVo.weight,
                healthy = slotVo.healthy,
                satiety = slotVo.satiety,
                strength = slotVo.strength,
                sleep = slotVo.sleep,
                payPoint = slotVo.payPoint,
                born = slotVo.born,
            )
        } else {
            SlotPickBackground()
            PageIndicator(
                pageIndicatorState = pageIndicatorState,
                modifier = Modifier.zIndex(1f)
            )
            SlotPickContent(
                slotVo = slotVo,
                starPoint = starPoint.value,
                buySlotPrice = buySlotPrice.value,
                isSlotEmpty = slotVo.shiftCode == ShiftCode.EMPTY,
                isSlotDisable = slotVoIndex.intValue >= maxSlot.value,
                { slotVoIndex.intValue = max(0, slotVoIndex.intValue - 1) },
                nextFood = { slotVoIndex.intValue = min(slotVoIndex.intValue + 1, 2) },
                addDialog = { slotPickViewModel.uiState.addDialog = true },
                deleteDialog = { slotPickViewModel.uiState.deleteDialog = true },
                pickDialog = { slotPickViewModel.uiState.pickDialog = true },
                buySlotDialog = { slotPickViewModel.uiState.buySlotDialog = true },
                detailDialog = { slotPickViewModel.uiState.detailDialog = true },
                modifier = Modifier.zIndex(2f)
            )
        }
    }

    if (slotPickViewModel.uiState.navMainPager) {
        scrollPage(2)
        navController.popBackStack(route = NavItem.SlotPick.route, inclusive = true)
    }

    LaunchedEffect(Unit) {
        slotPickViewModel.loadData()
    }
}

@Composable
private fun SlotPickLoadingBar(
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
private fun SlotPickContent(
    slotVo: SlotVo,
    starPoint: Int,
    buySlotPrice: Int,
    isSlotEmpty: Boolean,
    isSlotDisable: Boolean,
    preFood: () -> Unit,
    nextFood: () -> Unit,
    addDialog: () -> Unit,
    deleteDialog: () -> Unit,
    pickDialog: () -> Unit,
    buySlotDialog: () -> Unit,
    detailDialog: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
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
                if (!isSlotEmpty && !isSlotDisable) {
                    Text(
                        text = slotVo.name,
                        textAlign = TextAlign.Center,
                        fontFamily = DAL_MU_RI,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        color = PaymongWhite,
                        maxLines = 1,
                    )
                } else if (isSlotDisable) {
                    StarPoint(starPoint = starPoint)
                } else {
                    Text(
                        text = "새로운 몽 생성",
                        textAlign = TextAlign.Center,
                        fontFamily = DAL_MU_RI,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        color = PaymongWhite,
                        maxLines = 1,
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.52f)
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
                ) {
                    if (!isSlotEmpty && !isSlotDisable) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier.fillMaxSize(),
                        ) {
                            Mong(
                                isPng = true,
                                mong = MongResourceCode.valueOf(slotVo.mongCode),
                                onClick = detailDialog,
                                ratio = 0.65f,
                            )
                        }
                    } else if (isSlotDisable) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mong_shadow),
                                contentDescription = null,
                                modifier = Modifier
                                    .offset(y = 23.dp)
                                    .width(80.dp)
                                    .height(20.dp)
                                    .zIndex(1f)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .zIndex(2f)

                            ) {
                                Image(
                                    painter = painterResource(R.drawable.starpoint_logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(27.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "- $buySlotPrice",
                                    textAlign = TextAlign.Center,
                                    fontFamily = DAL_MU_RI,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 22.sp,
                                    color = PaymongWhite,
                                    maxLines = 1,
                                )
                            }
                        }
                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                modifier = Modifier.size(81.dp),
                                painter = painterResource(R.drawable.egg_blind),
                                contentDescription = null
                            )
                            Text(
                                text = "?",
                                textAlign = TextAlign.Center,
                                fontFamily = DAL_MU_RI,
                                fontWeight = FontWeight.Light,
                                fontSize = 25.sp,
                                color = PaymongWhite,
                                maxLines = 1,
                            )
                        }
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
                    .weight(0.28f)
            ) {
                if (!isSlotDisable && !isSlotEmpty) {
                    BlueButton(
                        text = "삭제",
                        height = 32,
                        width = 55,
                        onClick = deleteDialog,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    BlueButton(
                        text = "선택",
                        height = 32,
                        width = 55,
                        onClick = pickDialog,
                        disable = slotVo.isSelected || slotVo.shiftCode in arrayListOf(ShiftCode.DEAD, ShiftCode.DELETE, ShiftCode.GRADUATE)
                    )
                } else if (isSlotDisable) {
                    YellowButton(
                        text = "슬롯구매",
                        height = 33,
                        width = 75,
                        onClick = buySlotDialog,
                        disable = starPoint < buySlotPrice,
                    )
                } else {
                    BlueButton(
                        text = "생성",
                        height = 33,
                        width = 75,
                        onClick = addDialog,
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }

    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun SlotPickViewPreview() {
    Box {
        SlotPickBackground()
        PageIndicator(
            pageIndicatorState = remember {
                object : PageIndicatorState {
                    override val pageOffset: Float
                        get() = 0f
                    override val selectedPage: Int
                        get() = 0
                    override val pageCount: Int
                        get() = 3
                }
            },
            modifier = Modifier.zIndex(1f)
        )
        SlotPickContent(
            slotVo = SlotVo(mongCode = "CH300"),
            starPoint = 0,
            buySlotPrice = 1,
            isSlotEmpty = false,
            isSlotDisable = false,
            preFood = {},
            nextFood = {},
            addDialog = {},
            deleteDialog = {},
            pickDialog = {},
            buySlotDialog = {},
            detailDialog = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}