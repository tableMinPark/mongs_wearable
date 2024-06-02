package com.paymong.wear.ui.view.mainSlot

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.view.mainSlot.effect.EvolutionEffect
import com.paymong.wear.ui.view.mainSlot.effect.GraduationEffect
import com.paymong.wear.ui.view.mainSlot.effect.GraduationEndEffect
import com.paymong.wear.ui.view.mainSlot.effect.HeartEffect
import com.paymong.wear.ui.view.mainSlot.effect.PoopEffect
import com.paymong.wear.ui.viewModel.mainSlot.MainSlotViewModel.UiState

@Composable
fun MainSlotEffect(
    slotVo: SlotVo,
    isPageChanging: Boolean = false,
    evolution: () -> Unit,
    graduationCheck: () -> Unit,
    uiState: UiState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    when (slotVo.shiftCode) {
        ShiftCode.EVOLUTION_READY -> {
            if (!isPageChanging) {
                EvolutionEffect(
                    slotVo = slotVo,
                    isEvolution = uiState.isEvolution,
                    runEvolution = {
                        uiState.isEvolution = true
                                   Log.d("test", "${uiState.isEvolution}")
                                   },
                    evolution = evolution,
                    modifier = modifier,
                )
            }
        }
        ShiftCode.GRADUATE_READY -> {
            if (!isPageChanging && !slotVo.isGraduateCheck) {
                GraduationEffect(
                    graduationCheck = graduationCheck,
                    modifier = modifier,
                )
            }
        }
        else -> {
            if (slotVo.isHappy) {
                HeartEffect(modifier = modifier)
            } else if (slotVo.shiftCode == ShiftCode.GRADUATE) {
                GraduationEndEffect(
                    modifier = modifier,
                )
            }
            PoopEffect(
                poopCount = slotVo.poopCount,
                modifier = modifier,
            )
        }
    }
}
