package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.domain.slot.vo.SlotVo
import com.mongs.wear.presentation.assets.MongResourceCode
import com.mongs.wear.presentation.assets.NavItem

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