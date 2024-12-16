package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(): LiveData<SlotVo?> {

        return slotRepository.getNowSlotLive().map {
            it?.let { slotModel ->
                SlotVo(
                    mongId = slotModel.mongId,
                    name = slotModel.name,
                    mongCode = slotModel.mongCode,
                    weight = slotModel.weight,
                    healthy = slotModel.healthy,
                    satiety = slotModel.satiety,
                    strength = slotModel.strength,
                    sleep = slotModel.sleep,
                    poopCount = slotModel.poopCount,
                    isSleeping = slotModel.isSleeping,
                    exp = slotModel.exp,
                    stateCode = slotModel.stateCode,
                    shiftCode = slotModel.shiftCode,
                    payPoint = slotModel.payPoint,
                    born = slotModel.born,
                    isHappy = slotModel.isHappy,
                    isEating = slotModel.isEating,
                    isSelected = slotModel.isSelected,
                    isPoopCleaning = slotModel.isPoopCleaning,
                    isGraduateCheck = slotModel.isGraduateCheck,
                )
            } ?: run {
                null
            }
        }
    }
}