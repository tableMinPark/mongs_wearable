package com.paymong.wear.ui.view.main.slot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paymong.wear.ui.global.resource.MongResourceCode
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.refac.vo.SlotVo
import com.paymong.wear.ui.global.component.Mong
import com.paymong.wear.ui.view.main.slot.component.Dead
import com.paymong.wear.ui.view.main.slot.component.Empty
import com.paymong.wear.ui.view.main.slot.component.EvolutionEffect
import com.paymong.wear.ui.view.main.slot.component.EvolutionReady
import com.paymong.wear.ui.view.main.slot.component.GraduationEffect
import com.paymong.wear.ui.view.main.slot.component.Heart
import com.paymong.wear.ui.view.main.slot.component.Poop
import com.paymong.wear.ui.view.main.slot.component.Removed

@Composable
fun MainSlotContent(
    isPageChange: State<Boolean>,
    slotVo: State<SlotVo>,
    stroke: () -> Unit,
    evolution: () -> Unit,
    graduationCheck: () -> Unit,
    navSlotSelect: () -> Unit,
) {
    /** 몽 정보, 몽 상태 정보 **/
    val mong = MongResourceCode.valueOf(slotVo.value.mongCode)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        when (slotVo.value.shiftCode) {
            ShiftCode.NORMAL -> {
                // 기본
                Mong(
                    state = slotVo.value.stateCode,
                    isHappy = slotVo.value.isHappy,
                    isEating = slotVo.value.isEating,
                    isSleeping = slotVo.value.isSleeping,
                    mong = mong,
                    onClick = stroke,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 25.dp)
                )
            }
            ShiftCode.EVOLUTION_READY -> {
                // 진화 대기
                Mong(
                    state = slotVo.value.stateCode,
                    mong = mong,
                    onClick = stroke,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 25.dp)
                )
            }
            ShiftCode.DEAD -> {
                // 죽음
                Dead(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 25.dp)
                )
            }
            ShiftCode.EMPTY -> {
                // 몽 없음
                Empty(
                    isPageChange = isPageChange.value,
                    onClick = navSlotSelect,
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }
            ShiftCode.DELETE -> {
                // 몽 삭제
                Removed(
                    isPageChange = isPageChange.value,
                    onClick = navSlotSelect,
                    mongModifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 25.dp),
                    textModifier = Modifier
                        .align(Alignment.Center),
                )
            }
            else -> {
                // 진화 대기, 진화 중, 졸업
                Mong(
                    mong = mong,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 25.dp)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(2f)
    ) {
        when (slotVo.value.shiftCode) {
            ShiftCode.NORMAL -> {
                // 기본
                Poop(
                    poopCount = slotVo.value.poopCount,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                )
            }
            ShiftCode.DEAD -> {}
            ShiftCode.GRADUATION_READY -> {
                // 졸업
                GraduationEffect(
                    isGraduationCheck = slotVo.value.isGraduateCheck,
                    isPageChange = isPageChange.value,
                    onClick = navSlotSelect,
                    graduationCheck = graduationCheck,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            ShiftCode.EVOLUTION_READY -> {
                // 진화 대기
                EvolutionReady(
                    onClick = evolution,
                    isPageChange = isPageChange.value,
                )
            }
            ShiftCode.EVOLUTION -> {
                // 진화 중
                EvolutionEffect(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(2f)
    ) {
        if (slotVo.value.isHappy && slotVo.value.shiftCode == ShiftCode.NORMAL) {
            // 행복
            Heart(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 25.dp),
            )
        }
    }
}