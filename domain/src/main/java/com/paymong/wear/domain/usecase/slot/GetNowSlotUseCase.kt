package com.paymong.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.paymong.wear.domain.repositroy.SlotRepository
import com.paymong.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository
) {
    suspend operator fun invoke(): LiveData<SlotVo> {
        val slotModel = slotRepository.getNowSlotLive()

        return slotModel.map {
            SlotVo(
                mongId = it.mongId,
                name = it.name,
                mongCode = it.mongCode,
                weight = it.weight,
                healthy = it.healthy,
                satiety = it.satiety,
                strength = it.strength,
                sleep = it.sleep,
                poopCount = it.poopCount,
                isSleeping = it.isSleeping,
                exp = it.exp,
                stateCode = it.stateCode,
                shiftCode = it.shiftCode,
                payPoint = it.payPoint,
                born = it.born,
                isHappy = it.isHappy,
                isEating = it.isEating,
                isSelected = it.isSelected,
                isGraduateCheck = it.isGraduateCheck,
            )
        }
    }
}