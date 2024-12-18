package com.mongs.wear.domain.slot.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.repository.SlotRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class StrokeNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        val slotModel = slotRepository.getSlot(mongId = mongId)
        if (!slotModel.isHappy) {
            CoroutineScope(Dispatchers.IO).launch {
                managementRepository.setIsHappy(mongId = slotModel.mongId, isHappy = true)
                managementRepository.strokeMong(mongId = slotModel.mongId)
                delay(2000)
                managementRepository.setIsHappy(mongId = slotModel.mongId, isHappy = false)
            }
        }
    }
}