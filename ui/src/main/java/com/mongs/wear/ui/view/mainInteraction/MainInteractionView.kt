package com.mongs.wear.ui.view.mainInteraction

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
import androidx.compose.runtime.State
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
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import com.mongs.wear.ui.global.component.button.CircleButton
import com.mongs.wear.ui.global.resource.MongResourceCode
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.viewModel.mainInteraction.MainInteractionViewModel

@Composable
fun MainInteractionView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    slotVo: State<SlotVo>,
    mainInteractionViewModel: MainInteractionViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    val isEgg = MongResourceCode.valueOf(slotVo.value.mongCode).isEgg
    val isMongEmpty = slotVo.value.shiftCode == ShiftCode.EMPTY || slotVo.value.shiftCode == ShiftCode.DELETE || slotVo.value.shiftCode == ShiftCode.DEAD
    val isGraduateReady = slotVo.value.shiftCode == ShiftCode.GRADUATE_READY

    Box {
        MainInteractionContent(
            feed = {
                if (isMongEmpty || isEgg || slotVo.value.isSleeping) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(NavItem.FeedNested.route)
                }
            },
            collection = {
                navController.navigate(NavItem.CollectionNested.route)
            },
            sleeping = {
                if (isMongEmpty || isGraduateReady || isEgg) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    mainInteractionViewModel.sleeping()
                }
            },
            slotPick = {
                navController.navigate(NavItem.SlotPick.route)
            },
            poopClean = {
                if (isMongEmpty || isGraduateReady || isEgg ||  slotVo.value.isSleeping) {
                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
                } else {
                    mainInteractionViewModel.poopClean()
                }
            },
            training = {
                Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
//                if (isMongEmpty || isEgg ||  slotVo.value.isSleeping) {
//                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
//                } else {
//                    navController.navigate(NavItem.TrainingNested.route)
//                }
            },
            battle = {
                Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
//                if (isMongEmpty || isEgg || slotVo.value.isSleeping) {
//                    Toast.makeText(context, "불가능한 상태", Toast.LENGTH_SHORT).show()
//                } else {
//                    navController.navigate(NavItem.BattleNested.route)
//                }
            },
            modifier = Modifier.zIndex(1f)
        )
    }

    if (mainInteractionViewModel.uiState.navMainSlotView) {
        mainInteractionViewModel.uiState.navMainSlotView = false
        scrollPage(2)
    }

    if (mainInteractionViewModel.uiState.alertSleepingFail) {
        mainInteractionViewModel.uiState.alertSleepingFail = false
        Toast.makeText(context, "잠시후 다시 시도", Toast.LENGTH_SHORT).show()
    }

    if (mainInteractionViewModel.uiState.alertPoopCleanFail) {
        mainInteractionViewModel.uiState.alertPoopCleanFail = false
        Toast.makeText(context, "잠시후 다시 시도", Toast.LENGTH_SHORT).show()
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
                CircleButton(
                    icon = R.drawable.feed,
                    border = R.drawable.interaction_bnt_yellow,
                    onClick = feed,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleButton(
                    icon = R.drawable.collection,
                    border = R.drawable.interaction_bnt_orange,
                    onClick = collection,
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleButton(
                    icon = R.drawable.sleep,
                    border = R.drawable.interaction_bnt_blue,
                    onClick = sleeping,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleButton(
                    icon = R.drawable.slot,
                    border = R.drawable.interaction_bnt_red,
                    onClick = slotPick,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleButton(
                    icon = R.drawable.poop,
                    border = R.drawable.interaction_bnt_purple,
                    onClick = poopClean,
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleButton(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    onClick = training,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleButton(
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