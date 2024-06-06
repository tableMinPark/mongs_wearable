package com.mongs.wear.ui.view.mainSlot

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
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.viewModel.mainSlot.MainSlotViewModel
import com.mongs.wear.ui.viewModel.mainSlot.MainSlotViewModel.UiState

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
            isPageChanging = isPageChanging.value,
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
            modifier = Modifier.zIndex(1f),
        )
        MainSlotEffect(
            slotVo = slotVo,
            evolution = {},
            graduationCheck = {},
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
            modifier = Modifier.zIndex(1f),
        )
        MainSlotEffect(
            slotVo = slotVo,
            isPageChanging = false,
            evolution = {},
            graduationCheck = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(2f),
        )
    }
}