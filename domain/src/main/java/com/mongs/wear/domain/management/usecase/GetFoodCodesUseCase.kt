package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.repository.SlotRepository
import com.mongs.wear.domain.vo.FoodVo
import javax.inject.Inject

class GetFoodCodesUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(): List<FoodVo> {
        val slotModel = slotRepository.getNowSlot()
        val foodVos = ArrayList<FoodVo>()
        val feedLogModelMap = managementRepository.getFeedItems(mongId = slotModel.mongId).associateBy { it.foodTypeCode }

        return foodVos
    }
}