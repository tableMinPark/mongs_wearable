package com.mongs.wear.domain.usecase.feed

import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.FoodVo
import javax.inject.Inject

class GetFoodCodesUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(): List<FoodVo> {
        val slotModel = slotRepository.getNowSlot()
        val foodVos = ArrayList<FoodVo>()
        val feedLogModelMap = managementRepository.getFeedLog(mongId = slotModel.mongId).associateBy { it.code }

        return foodVos
    }
}