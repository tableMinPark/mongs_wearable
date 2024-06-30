package com.mongs.wear.ui.view.mainSlot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import com.mongs.wear.ui.global.resource.MongResourceCode
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.viewModel.mainSlot.MainSlotViewModel
import com.mongs.wear.ui.viewModel.mainSlot.MainSlotViewModel.UiState

@Composable
fun MainSlotView(
    navController: NavController,
    slotVo: SlotVo,
    isPageChanging: State<Boolean>,
    mainSlotViewModel: MainSlotViewModel = hiltViewModel(),
) {
    Box {
        val isEgg = MongResourceCode.valueOf(slotVo.mongCode).isEgg

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
        if (slotVo.isSleeping && !isPageChanging.value) {
            Box(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.5f))
                    .fillMaxSize()
                    .zIndex(3f)
            )
        }
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