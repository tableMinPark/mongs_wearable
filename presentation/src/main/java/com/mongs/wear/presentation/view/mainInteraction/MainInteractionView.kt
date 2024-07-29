package com.mongs.wear.presentation.view.mainInteraction

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.global.component.background.MainPagerBackground
import com.mongs.wear.presentation.global.component.button.CircleImageButton
import com.mongs.wear.presentation.global.resource.MongResourceCode
import com.mongs.wear.presentation.global.resource.NavItem
import com.mongs.wear.presentation.viewModel.mainInteraction.MainInteractionViewModel

@Composable
fun MainInteractionView(
    navController: NavController,
    slotVo: SlotVo,
    scrollPage: (Int) -> Unit,
    mainInteractionViewModel: MainInteractionViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    val isEgg = remember { derivedStateOf { MongResourceCode.valueOf(slotVo.mongCode).isEgg } }
    val isMongEmpty =  remember { derivedStateOf { slotVo.shiftCode == ShiftCode.EMPTY || slotVo.shiftCode == ShiftCode.DELETE || slotVo.shiftCode == ShiftCode.DEAD } }
    val isGraduateReady = remember { derivedStateOf {  slotVo.shiftCode == ShiftCode.GRADUATE_READY } }

    Box {
        MainInteractionContent(
            feed = {
                if (isMongEmpty.value || isEgg.value || slotVo.isSleeping) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(NavItem.FeedNested.route)
                }
            },
            collection = {
                navController.navigate(NavItem.CollectionNested.route)
            },
            sleeping = {
                if (isMongEmpty.value || isGraduateReady.value || isEgg.value) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    mainInteractionViewModel.sleeping(mongId = slotVo.mongId)
                }
            },
            slotPick = {
                navController.navigate(NavItem.SlotPick.route)
            },
            poopClean = {
                if (isMongEmpty.value || isGraduateReady.value || isEgg.value ||  slotVo.isSleeping) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    mainInteractionViewModel.poopClean(mongId = slotVo.mongId)
                }
            },
            training = {
                if (isMongEmpty.value || isEgg.value ||  slotVo.isSleeping) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(NavItem.TrainingNested.route)
                }
            },
            battle = {
                if (isMongEmpty.value || isEgg.value || slotVo.isSleeping) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(NavItem.BattleNested.route)
                }
            },
            modifier = Modifier.zIndex(1f)
        )
    }

    LaunchedEffect(mainInteractionViewModel.uiState.navMainSlotView) {
        if (mainInteractionViewModel.uiState.navMainSlotView) {
            scrollPage(2)
            mainInteractionViewModel.uiState.navMainSlotView = false
        }
    }
    LaunchedEffect(mainInteractionViewModel.uiState.alertSleepingFail) {
        if (mainInteractionViewModel.uiState.alertSleepingFail) {
            Toast.makeText(context, "잠시후 다시 시도", Toast.LENGTH_SHORT).show()
            mainInteractionViewModel.uiState.alertSleepingFail = false
        }
    }

    LaunchedEffect(mainInteractionViewModel.uiState.alertPoopCleanFail) {
        if (mainInteractionViewModel.uiState.alertPoopCleanFail) {
            Toast.makeText(context, "잠시후 다시 시도", Toast.LENGTH_SHORT).show()
            mainInteractionViewModel.uiState.alertPoopCleanFail = false
        }
    }
}

@Composable
private fun MainInteractionContent(
    feed: () -> Unit,
    collection: () -> Unit,
    sleeping: () -> Unit,
    slotPick: () -> Unit,
    poopClean: () -> Unit,
    training: () -> Unit,
    battle: () -> Unit,
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
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleImageButton(
                    icon = R.drawable.feed,
                    border = R.drawable.interaction_bnt_yellow,
                    onClick = feed,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.collection,
                    border = R.drawable.interaction_bnt_orange,
                    onClick = collection,
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleImageButton(
                    icon = R.drawable.sleep,
                    border = R.drawable.interaction_bnt_blue,
                    onClick = sleeping,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.slot,
                    border = R.drawable.interaction_bnt_red,
                    onClick = slotPick,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.poop,
                    border = R.drawable.interaction_bnt_purple,
                    onClick = poopClean,
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleImageButton(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    onClick = training,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = battle,
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainInteractionViewPreView() {
    Box {
        MainPagerBackground()
        MainInteractionContent(
            training = {},
            battle = {},
            feed = {},
            sleeping = {},
            poopClean = {},
            collection = {},
            slotPick = {},
            modifier = Modifier.zIndex(1f)
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeMainInteractionViewPreView() {
    Box {
        MainPagerBackground()
        MainInteractionContent(
            training = {},
            battle = {},
            feed = {},
            sleeping = {},
            poopClean = {},
            collection = {},
            slotPick = {},
            modifier = Modifier.zIndex(1f)
        )
    }
}