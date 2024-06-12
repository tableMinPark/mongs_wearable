package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class StrokeNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke() {
        try {
            val slotModel = slotRepository.getNowSlot()
            if (!slotModel.isHappy) {
                CoroutineScope(Dispatchers.IO).launch {
                    managementRepository.setIsHappy(mongId = slotModel.mongId, isHappy = true)
                    managementRepository.strokeMong(mongId = slotModel.mongId)
                    delay(2000)
                    managementRepository.setIsHappy(mongId = slotModel.mongId, isHappy = false)
                }
            }
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}