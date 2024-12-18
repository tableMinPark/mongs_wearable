package com.mongs.wear.domain.slot.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.slot.repository.SlotRepository
import com.mongs.wear.domain.slot.vo.SlotVo
import javax.inject.Inject

class GetNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(): LiveData<SlotVo?> {

        return slotRepository.getNowSlotLive().map {
            it?.let { slotModel ->
                SlotVo(
                    mongId = slotModel.mongId,
                    mongName = slotModel.mongName,
                    mongTypeCode = slotModel.mongTypeCode,
                    weight = slotModel.weight,
                    healthy = slotModel.healthyRatio,
                    satiety = slotModel.satietyRatio,
                    strength = slotModel.strengthRatio,
                    sleep = slotModel.fatigueRatio,
                    poopCount = slotModel.poopCount,
                    isSleeping = slotModel.isSleeping,
                    exp = slotModel.expRatio,
                    stateCode = slotModel.statusCode,
                    shiftCode = slotModel.stateCode,
                    payPoint = slotModel.payPoint,
                    born = slotModel.createdAt,
                    isHappy = slotModel.isHappy,
                    isEating = slotModel.isEating,
                    isSelected = slotModel.isCurrent,
                    isPoopCleaning = slotModel.isPoopCleaning,
                    isGraduateCheck = slotModel.graduateCheck,
                )
            } ?: run {
                null
            }
        }
    }
}