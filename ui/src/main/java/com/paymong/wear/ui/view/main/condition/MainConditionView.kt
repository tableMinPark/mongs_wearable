package com.paymong.wear.ui.view.main.condition

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.paymong.wear.domain.vo.SlotVo

@Composable
fun MainConditionView(
    slotVo: State<SlotVo>,
    isPageChange: State<Boolean>,
) {
    Box {
        MainConditionContent(
            isPageChange = isPageChange,
            slotVo = slotVo,
        )
    }
}
