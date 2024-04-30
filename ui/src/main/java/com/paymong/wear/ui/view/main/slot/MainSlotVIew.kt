package com.paymong.wear.ui.view.main.slot

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.repository.slot.vo.SlotVo
import com.paymong.wear.domain.viewModel.main.MainSlotViewModel
import com.paymong.wear.ui.global.resource.NavItem

@Composable
fun MainSlotView(
    slotVo: State<SlotVo>,
    isPageChange: State<Boolean>,
    navController: NavController,
    scrollPage: (Int) -> Unit,
    mainSlotViewModel: MainSlotViewModel = hiltViewModel()
) {
    Box {
        MainSlotContent(
            isPageChange = isPageChange,
            slotVo = slotVo,
            stroke = mainSlotViewModel::stroke,
            evolution = mainSlotViewModel::evolution,
            graduationCheck = mainSlotViewModel::graduationCheck,
            navSlotSelect = {
                scrollPage(2)
                navController.navigate(NavItem.SlotSelect.route)
            }
        )
    }
}
