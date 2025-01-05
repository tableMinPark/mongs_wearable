package com.mongs.wear.domain.management.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.management.exception.FeedMongException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.management.repository.SlotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedMongUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) : BaseParamUseCase<FeedMongUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {
            slotRepository.getCurrentSlot()?.let { slotModel ->
                managementRepository.feedMong(mongId = slotModel.mongId, foodTypeCode = param.foodTypeCode)
            } ?: run {  }

//        CoroutineScope(Dispatchers.IO).launch {
//            val slotModel = slotRepository.getNowSlot()
//            managementRepository.setIsEating(mongId = slotModel.mongId, isEating = true)
//            managementRepository.feedMong(mongId = slotModel.mongId, foodTypeCode = foodTypeCode)
//            delay(2000)
//            managementRepository.setIsEating(mongId = slotModel.mongId, isEating = false)
//        }
        }
    }

    data class Param(
        val foodTypeCode: String,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw FeedMongException()
    }
}