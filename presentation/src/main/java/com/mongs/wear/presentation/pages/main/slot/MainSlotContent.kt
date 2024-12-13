package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.presentation.pages.main.slot.content.DeadContent
import com.mongs.wear.presentation.pages.main.slot.content.DeleteContent
import com.mongs.wear.presentation.pages.main.slot.content.EmptyContent
import com.mongs.wear.presentation.pages.main.slot.content.GraduatedContent
import com.mongs.wear.presentation.pages.main.slot.content.NormalContent
import com.mongs.wear.presentation.pages.main.slot.MainSlotViewModel.UiState

@Composable
fun MainSlotContent(
    slotVo: SlotVo,
    isPageChanging: Boolean,
    stroke: () -> Unit,
    navSlotPick: () -> Unit,
    uiState: UiState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    when (slotVo.shiftCode) {
        ShiftCode.NORMAL -> {
            NormalContent(
                slotVo = slotVo,
                stroke = stroke,
                modifier = modifier,
            )
        }
        ShiftCode.DEAD -> {
            DeadContent(
                modifier = modifier,
            )
        }
        ShiftCode.GRADUATE_READY -> {
            NormalContent(
                slotVo = slotVo,
                stroke = {},
                modifier = modifier,
            )
        }
        ShiftCode.EVOLUTION_READY -> {
            if (!uiState.isEvolution) {
                NormalContent(
                    slotVo = slotVo,
                    stroke = {},
                    modifier = modifier,
                )
            }
        }
        ShiftCode.GRADUATE -> {
            GraduatedContent(
                slotVo = slotVo,
                isPageChanging = isPageChanging,
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
                isPageChanging = isPageChanging,
                onClick = navSlotPick,
                modifier = modifier,
            )
        }
        else -> {}
    }
}