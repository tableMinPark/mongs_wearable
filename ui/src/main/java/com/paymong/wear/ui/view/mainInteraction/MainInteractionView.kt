package com.paymong.wear.ui.view.mainInteraction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.background.MainPagerBackground
import com.paymong.wear.ui.global.component.button.CircleButton
import com.paymong.wear.ui.viewModel.mainInteraction.MainInteractionViewModel
import com.paymong.wear.ui.viewModel.mainInteraction.MainInteractionViewModel.UiState

@Composable
fun MainInteractionView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    slotVo: State<SlotVo>,
    mainInteractionViewModel: MainInteractionViewModel = hiltViewModel(),
) {
    Box {
        MainInteractionContent(
            slotVo = slotVo.value,
            training = { /*TODO*/ },
            battle = { /*TODO*/ },
            feed = { /*TODO*/ },
            sleeping = { /*TODO*/ },
            poopClean = { /*TODO*/ },
            collection = { /*TODO*/ },
            slotPick = { /*TODO*/ },
            uiState = mainInteractionViewModel.uiState,
            modifier = Modifier.zIndex(1f)
        )
    }

    LaunchedEffect(Unit) {
        val isMongEmpty = slotVo.value.shiftCode == ShiftCode.EMPTY || slotVo.value.shiftCode == ShiftCode.DELETE || slotVo.value.shiftCode == ShiftCode.DEAD
        val isGraduateReady = slotVo.value.shiftCode == ShiftCode.GRADUATE_READY

        mainInteractionViewModel.uiState.
    }
}

@Composable
private fun MainInteractionContent(
    slotVo: SlotVo,
    training: () -> Unit,
    battle: () -> Unit,
    feed: () -> Unit,
    sleeping: () -> Unit,
    poopClean: () -> Unit,
    collection: () -> Unit,
    slotPick: () -> Unit,
    uiState: UiState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    val isEgg = slotVo.mongCode in listOf("CH000", "CH001", "CH002", "CH003", "CH004", "CH005")

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
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(end = 4.dp),
                )
                CircleButton(
                    icon = R.drawable.collection,
                    border = R.drawable.interaction_bnt_orange,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleButton(
                    icon = R.drawable.sleep,
                    border = R.drawable.interaction_bnt_blue,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(end = 4.dp)
                )
                CircleButton(
                    icon = R.drawable.slot,
                    border = R.drawable.interaction_bnt_red,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                )
                CircleButton(
                    icon = R.drawable.poop,
                    border = R.drawable.interaction_bnt_purple,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleButton(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(end = 4.dp)
                )
                CircleButton(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 4.dp)
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
            slotVo = SlotVo(),
            modifier = Modifier.zIndex(1f)
        )
    }
}