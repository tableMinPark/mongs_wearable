package com.paymong.wear.domain.usecase.slot

import com.paymong.wear.domain.repositroy.SlotRepository
import com.paymong.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val slotRepository: SlotRepository
) {
    suspend operator fun invoke(): List<SlotVo> {
        val slotModels = slotRepository.getSlots()

        return slotModels.map {
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