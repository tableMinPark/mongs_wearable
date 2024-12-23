package com.mongs.wear.presentation.pages.main.slot.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mongs.wear.domain.slot.vo.SlotVo
import com.mongs.wear.presentation.assets.MongResourceCode
import com.mongs.wear.presentation.component.common.Mong

@Composable
fun NormalContent(
    slotVo: SlotVo,
    stroke: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.fillMaxSize(),
    ) {
        Mong(
            state = slotVo.statusCode,
            isHappy = slotVo.isHappy,
            isEating = slotVo.isEating,
            isSleeping = slotVo.isSleeping,
            mong = MongResourceCode.valueOf(slotVo.mongTypeCode),
            onClick = stroke,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .zIndex(1f)
        )
    }
}