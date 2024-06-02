package com.paymong.wear.ui.view.mainSlot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.global.component.common.Mong
import com.paymong.wear.ui.global.resource.MongResourceCode
import com.paymong.wear.ui.view.mainSlot.content.DeadContent
import com.paymong.wear.ui.view.mainSlot.content.DeleteContent
import com.paymong.wear.ui.view.mainSlot.content.EmptyContent

@Composable
fun MainSlotContent(
    slotVo: SlotVo,
    stroke: () -> Unit,
    navSlotPick: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    when (slotVo.shiftCode) {
        ShiftCode.DEAD -> {
            DeadContent(
                modifier = modifier,
            )
        }
        ShiftCode.EMPTY -> {
            EmptyContent(
                onClick = navSlotPick,
                modifier = modifier,
            )
        }
        ShiftCode.DELETE -> {
            DeleteContent(
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