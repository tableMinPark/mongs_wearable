package com.mongs.wear.domain.usecase.feed

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

class FeedUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(code: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val slotModel = slotRepository.getNowSlot()
                managementRepository.setIsEating(mongId = slotModel.mongId, isEating = true)
                managementRepository.feedMong(mongId = slotModel.mongId, code = code)
                delay(2000)
                managementRepository.setIsEating(mongId = slotModel.mongId, isEating = false)
            }
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MANAGEMENT.groupCode,
                location = "FeedUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}