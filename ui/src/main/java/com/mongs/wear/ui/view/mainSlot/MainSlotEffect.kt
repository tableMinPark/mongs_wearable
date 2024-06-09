package com.mongs.wear.ui.view.mainSlot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.ui.view.mainSlot.effect.EvolutionEffect
import com.mongs.wear.ui.view.mainSlot.effect.GraduationEffect
import com.mongs.wear.ui.view.mainSlot.effect.GraduatedEffect
import com.mongs.wear.ui.view.mainSlot.effect.HeartEffect
import com.mongs.wear.ui.view.mainSlot.effect.PoopEffect
import com.mongs.wear.ui.viewModel.mainSlot.MainSlotViewModel.UiState

@Composable
fun MainSlotEffect(
    slotVo: SlotVo,
    isPageChanging: Boolean,
    evolution: () -> Unit,
    graduationReady: () -> Unit,
    uiState: UiState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    when (slotVo.shiftCode) {
        ShiftCode.NORMAL -> {
            if (slotVo.isHappy) {
                HeartEffect(modifier = modifier)
            }
            PoopEffect(
                poopCount = slotVo.poopCount,
                modifier = modifier,
            )
        }
        ShiftCode.DEAD -> {}
        ShiftCode.GRADUATE_READY -> {
            if (!isPageChanging && !slotVo.isGraduateCheck) {
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
        ShiftCode.EVOLUTION_READY -> {
            if (!isPageChanging) {
                EvolutionEffect(
                    slotVo = slotVo,
                    isEvolution = uiState.isEvolution,
                    runEvolution = { uiState.isEvolution = true },
                    evolution = evolution,
                    modifier = modifier,
                )
            }
        }
        ShiftCode.GRADUATE -> {}
        ShiftCode.EMPTY -> {}
        ShiftCode.DELETE -> {}
        else -> {}
    }
}
