package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.presentation.assets.MongResourceCode
import com.mongs.wear.presentation.assets.NavItem

@Composable
fun MainSlotView(
    navController: NavController,
    mongVo: MongVo,
    isPageChanging: State<Boolean>,
    mainSlotViewModel: MainSlotViewModel = hiltViewModel(),
) {
    Box {
        val isEgg = MongResourceCode.valueOf(mongVo.mongTypeCode).isEgg

        MainSlotContent(
            mongVo = mongVo,
            isPageChanging = isPageChanging.value,
            stroke = {
                if (!isEgg && !mongVo.isSleeping) {
                    mainSlotViewModel.stroke(mongId = mongVo.mongId)
                }
            },
            navSlotPick = {
                navController.navigate(NavItem.SlotPick.route)
            },
            uiState = mainSlotViewModel.uiState,
            modifier = Modifier.zIndex(1f)
        )
        MainSlotEffect(
            mongVo = mongVo,
            isPageChanging = isPageChanging.value,
            evolution = { mongId ->
                mainSlotViewModel.evolution(mongId)
            },
            graduationReady = {
                mainSlotViewModel.graduationReady(mongId = mongVo.mongId)
            },
            uiState = mainSlotViewModel.uiState,
            modifier = Modifier.zIndex(2f),
        )
    }
}