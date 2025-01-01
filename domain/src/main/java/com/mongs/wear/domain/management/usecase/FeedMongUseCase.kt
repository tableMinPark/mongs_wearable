package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.management.repository.SlotRepository
import javax.inject.Inject

class FeedMongUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(foodTypeCode: String) {

        slotRepository.getCurrentSlot()?.let { slotModel ->

            managementRepository.feedMong(mongId = slotModel.mongId, foodTypeCode = foodTypeCode)

            managementRepository.setIsEating(mongId = slotModel.mongId)

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