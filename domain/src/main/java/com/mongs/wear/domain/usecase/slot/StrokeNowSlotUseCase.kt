package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
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
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        try {
            val slotModel = slotRepository.getSlot(mongId = mongId)
            if (!slotModel.isHappy) {
                CoroutineScope(Dispatchers.IO).launch {
                    managementRepository.setIsHappy(mongId = slotModel.mongId, isHappy = true)
                    managementRepository.strokeMong(mongId = slotModel.mongId)
                    delay(2000)
                    managementRepository.setIsHappy(mongId = slotModel.mongId, isHappy = false)
                }
            }
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MANAGEMENT.groupCode,
                location = "StrokeNowSlotUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}