package com.paymong.wear.ui.view.mainSlot

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.global.component.background.MainPagerBackground
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.viewModel.mainSlot.MainSlotViewModel
import com.paymong.wear.ui.viewModel.mainSlot.MainSlotViewModel.UiState

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
            uiState = mainSlotViewModel.uiState,
            modifier = Modifier.zIndex(2f),
        )
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
            evolution = {},
            graduationCheck = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(2f),
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
            evolution = {},
            graduationCheck = {},
            uiState = UiState(),
            modifier = Modifier.zIndex(2f),
        )
    }
}