package com.paymong.wear.ui.view.mainSlot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.paymong.wear.ui.global.component.background.MainPagerBackground
import com.paymong.wear.ui.global.component.common.Mong
import com.paymong.wear.ui.global.resource.MongResourceCode
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.viewModel.mainSlot.MainSlotViewModel

@Composable
fun MainSlotView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    slotVo: State<SlotVo>,
    isPageChanging: State<Boolean>,
    mainSlotViewModel: MainSlotViewModel = hiltViewModel(),
) {
    Box {
        MainSlotContent(
            slotVo = slotVo.value,
            stroke = mainSlotViewModel::stroke,
            navSlotPick = {
                scrollPage(2)
                navController.navigate(NavItem.SlotPick.route)
            },
            modifier = Modifier.zIndex(1f)
        )
        MainSlotEffect(
            slotVo = slotVo.value,
            isPageChanging = isPageChanging.value,
            evolution = mainSlotViewModel::evolution,
            graduationCheck = mainSlotViewModel::graduationCheck,
            modifier = Modifier.zIndex(2f),
        )
    }
}

@Composable
private fun MainSlotContent(
    slotVo: SlotVo,
    stroke: () -> Unit,
    navSlotPick: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    when (slotVo.shiftCode) {
        ShiftCode.EVOLUTION_READY -> {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = modifier.fillMaxSize(),
            ) {
                Mong(
                    state = slotVo.stateCode,
                    mong = MongResourceCode.valueOf(slotVo.mongCode),
                    onClick = stroke,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
        ShiftCode.DEAD -> {
            Dead(
                modifier = modifier,
            )
        }
        ShiftCode.EMPTY -> {
            Empty(
                onClick = navSlotPick,
                modifier = modifier,
            )
        }
        ShiftCode.DELETE -> {
            Delete(
                onClick = navSlotPick,
                modifier = modifier,
            )
        }
        else -> {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = modifier.fillMaxSize(),
            ) {
                Mong(
                    state = slotVo.stateCode,
                    isHappy = slotVo.isHappy,
                    isEating = slotVo.isEating,
                    isSleeping = slotVo.isSleeping,
                    mong = MongResourceCode.valueOf(slotVo.mongCode),
                    onClick = stroke,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun MainSlotEffect(
    slotVo: SlotVo,
    isPageChanging: Boolean = false,
    evolution: () -> Unit,
    graduationCheck: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    when (slotVo.shiftCode) {
        ShiftCode.NORMAL -> {
            if (slotVo.isHappy) {
                Heart(modifier = modifier)
            }
            Poop(
                poopCount = slotVo.poopCount,
                modifier = modifier,
            )
        }
        ShiftCode.DEAD -> {}
        ShiftCode.GRADUATE -> {
            GraduationEnd(
                modifier = modifier,
            )
        }
        ShiftCode.GRADUATE_READY -> {
            if (!isPageChanging && !slotVo.isGraduateCheck) {
                GraduationEffect(
                    graduationCheck = graduationCheck,
                    modifier = modifier,
                )
            }
        }
        ShiftCode.EVOLUTION_READY -> {
            if (!isPageChanging) {
                EvolutionReadyEffect(
                    onClick = evolution,
                    modifier = modifier,
                )
            }
        }
        ShiftCode.EVOLUTION -> {
            if (!isPageChanging) {
                EvolutionEffect(
                    modifier = modifier,
                )
            }
        }
        else -> {}
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainSlotViewPreview() {
    Box {
        MainPagerBackground()
        MainSlotContent(
            slotVo = SlotVo(mongCode = "CH100", shiftCode = ShiftCode.NORMAL),
            stroke = {},
            navSlotPick = {},
            modifier = Modifier.zIndex(1f),
        )
        MainSlotEffect(
            slotVo = SlotVo(mongCode = "CH100", shiftCode = ShiftCode.NORMAL, isHappy = true),
            modifier = Modifier.zIndex(2f),
            evolution = {},
            graduationCheck = {},
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun MainSlotViewLargePreview() {
    Box {
        MainPagerBackground()
        MainSlotContent(
            slotVo = SlotVo(mongCode = "CH100", shiftCode = ShiftCode.NORMAL, isHappy = true),
            stroke = {},
            navSlotPick = {},
            modifier = Modifier.zIndex(1f),
        )
        MainSlotEffect(
            slotVo = SlotVo(mongCode = "CH100", shiftCode = ShiftCode.NORMAL, isHappy = true),
            isPageChanging = false,
            modifier = Modifier.zIndex(2f),
            evolution = {},
            graduationCheck = {},
        )
    }
}