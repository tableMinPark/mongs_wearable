package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.presentation.pages.main.slot.MainSlotViewModel.UiState
import com.mongs.wear.presentation.pages.main.slot.content.DeadContent
import com.mongs.wear.presentation.pages.main.slot.content.DeleteContent
import com.mongs.wear.presentation.pages.main.slot.content.EmptyContent
import com.mongs.wear.presentation.pages.main.slot.content.GraduatedContent
import com.mongs.wear.presentation.pages.main.slot.content.NormalContent

@Composable
fun MainSlotContent(
    mongVo: MongVo,
    isPageChanging: Boolean,
    stroke: () -> Unit,
    navSlotPick: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
    uiState: UiState,
) {
    when (mongVo.stateCode) {
        MongStateCode.NORMAL -> {
            NormalContent(
                mongVo = mongVo,
                stroke = stroke,
                modifier = modifier,
            )
        }
        MongStateCode.DEAD -> {
            DeadContent(
                modifier = modifier,
            )
        }
        MongStateCode.GRADUATE_READY -> {
            NormalContent(
                mongVo = mongVo,
                stroke = {},
                modifier = modifier,
            )
        }
        MongStateCode.EVOLUTION_READY -> {
            if (!uiState.isEvolution) {
                NormalContent(
                    mongVo = mongVo,
                    stroke = {},
                    modifier = modifier,
                )
            }
        }
        MongStateCode.GRADUATE -> {
            GraduatedContent(
                mongVo = mongVo,
                isPageChanging = isPageChanging,
                modifier = modifier,
            )
        }
        MongStateCode.EMPTY -> {
            EmptyContent(
                onClick = navSlotPick,
                modifier = modifier,
            )
        }
        MongStateCode.DELETE -> {
            DeleteContent(
                isPageChanging = isPageChanging,
                onClick = navSlotPick,
                modifier = modifier,
            )
        }
        else -> {}
    }
}