package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.slot.vo.SlotVo
import com.mongs.wear.presentation.assets.MongResourceCode
import com.mongs.wear.presentation.assets.NavItem
import com.mongs.wear.presentation.component.background.MainPagerBackground
import com.mongs.wear.presentation.pages.main.slot.MainSlotViewModel.UiState

@Composable
fun MainSlotView(
    navController: NavController,
    slotVo: SlotVo,
    isPageChanging: State<Boolean>,
    mainSlotViewModel: MainSlotViewModel = hiltViewModel(),
) {
    Box {
        val isEgg = MongResourceCode.valueOf(slotVo.mongTypeCode).isEgg

        MainSlotContent(
            slotVo = slotVo,
            isPageChanging = isPageChanging.value,
            stroke = {
                if (!isEgg && !slotVo.isSleeping) {
                    mainSlotViewModel.stroke(mongId = slotVo.mongId)
                }
            },
            navSlotPick = {
                navController.navigate(NavItem.SlotPick.route)
            },
            uiState = mainSlotViewModel.uiState,
            modifier = Modifier.zIndex(1f)
        )
        MainSlotEffect(
            slotVo = slotVo,
            isPageChanging = isPageChanging.value,
            evolution = { mongId ->
                mainSlotViewModel.evolution(mongId)
            },
            graduationReady = {
                mainSlotViewModel.graduationReady(mongId = slotVo.mongId)
            },
            uiState = mainSlotViewModel.uiState,
            modifier = Modifier.zIndex(2f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainSlotViewPreview() {
    val slotVo = SlotVo(
        shiftCode = ShiftCode.NORMAL,
        isHappy = true,
        isGraduateCheck = true,
    )
    Box {
        MainPagerBackground()
        MainSlotContent(
            slotVo = slotVo,
            isPageChanging = false,
            stroke = {},
            navSlotPick = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(1f),
        )
        MainSlotEffect(
            slotVo = slotVo,
            evolution = {},
            graduationReady = {},
            isPageChanging = false,
            uiState = UiState(),
            modifier = Modifier.zIndex(2f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun MainSlotViewLargePreview() {
    val slotVo = SlotVo(
        shiftCode = ShiftCode.NORMAL,
        isHappy = true,
        isGraduateCheck = true,
    )
    Box {
        MainPagerBackground()
        MainSlotContent(
            slotVo = slotVo,
            isPageChanging = false,
            stroke = {},
            navSlotPick = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(1f),
        )
        MainSlotEffect(
            slotVo = slotVo,
            isPageChanging = false,
            evolution = {},
            graduationReady = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(2f),
        )
    }
}