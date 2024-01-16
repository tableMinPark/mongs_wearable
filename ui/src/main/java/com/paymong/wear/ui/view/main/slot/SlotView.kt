package com.paymong.wear.ui.view.main.slot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.viewModel.code.SlotCode
import com.paymong.wear.domain.viewModel.main.SlotViewModel
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.code.ShiftCode
import com.paymong.wear.ui.code.StateCode
import com.paymong.wear.ui.view.common.character.Character

@Composable
fun SlotView(
    navController: NavController,
    showActionContent: () -> Unit,
    mongCode: State<String>,
    stateCode: State<String>,
    shiftCode: State<String>,
    poopCount: State<Int>,
    slotViewModel: SlotViewModel = hiltViewModel()
) {
    /** Flag **/
    val processCode = slotViewModel.processCode.observeAsState(SlotCode.STAND_BY)

    /** Logic by ProcessCode **/
    when(processCode.value) {
        SlotCode.GENERATE_MONG -> {
            SlotProcess()
        }
        SlotCode.NAVIGATE -> {
            navController.navigate(NavItem.MainNested.route) {
                popUpTo(
                    navController.graph.id
                )
            }
        }
        else -> {
            SlotContent(
                mongCode = mongCode,
                shiftCode = shiftCode,
                stateCode = stateCode,
                poopCount = poopCount,
                evolutionStart = slotViewModel::evolutionStart,
                evolutionEnd = slotViewModel::evolutionEnd,
                graduation = slotViewModel::graduation,
                generateMong = slotViewModel::generateMong,
                showSlotActionView = showActionContent
            )
        }
    }
}

@Composable
fun SlotContent(
    mongCode: State<String>,
    shiftCode: State<String>,
    stateCode: State<String>,
    poopCount: State<Int>,
    evolutionStart: () -> Unit,
    evolutionEnd: () -> Unit,
    graduation: () -> Unit,
    generateMong: () -> Unit,
    showSlotActionView: () -> Unit
) {
    /** Data **/
    val mong = MongCode.valueOf(mongCode.value)
    val shift = ShiftCode.valueOf(shiftCode.value)
    val state = StateCode.valueOf(stateCode.value)
    val poop = poopCount.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
            .zIndex(0f)
    ) {
        val zIndex = if (shift in arrayListOf(
                ShiftCode.SH002,
                ShiftCode.SH004
            )
        ) 1f else -1f

        /** Main Layer **/
        Box(
            modifier = Modifier.zIndex(0f)
        ) {
            // 몽이 없는 경우
            if (state == StateCode.CD444) {
                Empty(onClick = generateMong)
            }
            // 죽은 경우
            else if (shift == ShiftCode.SH001) {
                Dead()
            }
            // 수면, 먹는중, 행복 상태인 경우
            else if (state in arrayListOf(
                StateCode.CD002,
                StateCode.CD008,
                StateCode.CD009
            )) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Character(
                        state = state,
                        mong = mong,
                        showSlotActionView = showSlotActionView
                    )
                }
            }
            // 진화 대기 경우
            else if (shift == ShiftCode.SH003) {
                EvolutionReady(evolutionStart = evolutionStart)
            }
            // 진화 중인 경우
            else if (shift == ShiftCode.SH004) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Character(mong = mong)
                }
            }
            // 그 외의 경우
            else {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Character(
                        state = state,
                        mong = mong,
                        showSlotActionView = showSlotActionView
                    )
                }
            }
        }

        /** Sub Layer **/
        Box(
            modifier = Modifier
                .zIndex(2f)
        ) {
            // 몽이 존재 하고 변화 상태가 아닌 경우
            if (state != StateCode.CD444 && shift == ShiftCode.SH444) {
                Poop(poop)
            }
        }

        /** Animation Layer **/
        Box(
            modifier = Modifier
                .zIndex(zIndex)
        ) {
            // 쓰다 듬기 한 경우 (수면 중 일 수가 없음)
            if (state == StateCode.CD009) {
                Heart()
            }
            // 졸업한 경우 (수면 중 일 수가 없음)
            else if (shift == ShiftCode.SH002) {
                GraduationEffect(graduation = graduation)
            }
            // 진화 중인 경우 (수면 중 일 수가 없음)
            else if (shift == ShiftCode.SH004) {
                EvolutionEffect(evolutionEnd = evolutionEnd)
            }
        }
    }
}