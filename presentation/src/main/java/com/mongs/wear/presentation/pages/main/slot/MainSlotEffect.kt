package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.presentation.pages.main.slot.MainSlotViewModel.UiState
import com.mongs.wear.presentation.pages.main.slot.effect.EvolutionEffect
import com.mongs.wear.presentation.pages.main.slot.effect.GraduatedEffect
import com.mongs.wear.presentation.pages.main.slot.effect.GraduationEffect
import com.mongs.wear.presentation.pages.main.slot.effect.HeartEffect
import com.mongs.wear.presentation.pages.main.slot.effect.PoopCleanEffect
import com.mongs.wear.presentation.pages.main.slot.effect.PoopEffect
import com.mongs.wear.presentation.pages.main.slot.effect.SleepEffect

@Composable
fun MainSlotEffect(
    mongVo: MongVo,
    isPageChanging: Boolean,
    evolution: (Long) -> Unit,
    graduationReady: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
    uiState: UiState,
) {
    when (mongVo.stateCode) {
        MongStateCode.NORMAL -> {
            if (mongVo.isHappy) {
                HeartEffect(modifier = modifier)
            } else if (mongVo.isSleeping) {
                SleepEffect(modifier = modifier)
            } else if (mongVo.isPoopCleaning) {
                PoopCleanEffect(modifier = modifier)
            }

            PoopEffect(
                poopCount = mongVo.poopCount,
                modifier = modifier,
            )
        }
        MongStateCode.DEAD -> {}
        MongStateCode.GRADUATE_READY -> {
            if (!isPageChanging && !mongVo.graduateCheck) {
                GraduationEffect(
                    graduationReady = graduationReady,
                    modifier = modifier,
                )
            } else if(!isPageChanging) {
                GraduatedEffect(
                    modifier = modifier,
                )
            }
        }
        MongStateCode.EVOLUTION_READY -> {
            if (!isPageChanging) {
                EvolutionEffect(
                    mongVo = mongVo,
                    isEvolution = uiState.isEvolution,
                    runEvolution = { uiState.isEvolution = true },
                    evolution = { mongId -> evolution(mongId) },
                    modifier = modifier,
                )
            }
        }
        MongStateCode.GRADUATE -> {}
        MongStateCode.EMPTY -> {}
        MongStateCode.DELETE -> {}
        else -> {}
    }
}
