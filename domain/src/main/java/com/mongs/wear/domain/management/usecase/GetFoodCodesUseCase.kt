package com.mongs.wear.domain.management.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import com.mongs.wear.domain.management.exception.GetFoodCodesException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.management.repository.SlotRepository
import com.mongs.wear.domain.management.vo.FeedItemVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFoodCodesUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) : BaseNoParamUseCase<List<FeedItemVo>>() {

    companion object {
        private const val FOOD_TYPE_GROUP_CODE = "FD"
    }

    override suspend fun execute(): List<FeedItemVo> {

        return withContext(Dispatchers.IO) {
            slotRepository.getCurrentSlot()?.let { slotModel ->
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
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw GetFoodCodesException()
        }
    }
}