package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.domain.slot.vo.SlotVo
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
    slotVo: SlotVo,
    isPageChanging: Boolean,
    evolution: (Long) -> Unit,
    graduationReady: () -> Unit,
    uiState: UiState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    when (slotVo.stateCode) {
        MongStateCode.NORMAL -> {
            if (slotVo.isHappy) {
                HeartEffect(modifier = modifier)
            } else if (slotVo.isSleeping) {
                SleepEffect(modifier = modifier)
            } else if (slotVo.isPoopCleaning) {
                PoopCleanEffect(modifier = modifier)
            }

            PoopEffect(
                poopCount = slotVo.poopCount,
                modifier = modifier,
            )
        }
        MongStateCode.DEAD -> {}
        MongStateCode.GRADUATE_READY -> {
            if (!isPageChanging && !slotVo.graduateCheck) {
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
                    slotVo = slotVo,
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
