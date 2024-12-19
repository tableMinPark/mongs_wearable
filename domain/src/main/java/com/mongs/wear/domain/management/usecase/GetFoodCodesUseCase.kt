package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.management.vo.FeedItemVo
import com.mongs.wear.domain.slot.repository.SlotRepository
import javax.inject.Inject

class GetFoodCodesUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {

    companion object {
        private const val FOOD_TYPE_GROUP_CODE = "FD"
    }

    suspend operator fun invoke(): List<FeedItemVo> = slotRepository.getCurrentSlot()?.let { slotModel ->

        managementRepository.getFeedItems(mongId = slotModel.mongId, foodTypeGroupCode = FOOD_TYPE_GROUP_CODE).map { //.associateBy { it.foodTypeCode }

            FeedItemVo(
                foodTypeCode = it.foodTypeCode,
                foodTypeName = it.foodTypeName,
                price = it.price,
                isCanBuy = it.isCanBuy,
                addWeightValue = it.addWeightValue,
                addStrengthValue = it.addStrengthValue,
                addSatietyValue = it.addSatietyValue,
                addHealthyValue = it.addHealthyValue,
                addFatigueValue = it.addFatigueValue,
            )
        }
    } ?: run { ArrayList() }
}