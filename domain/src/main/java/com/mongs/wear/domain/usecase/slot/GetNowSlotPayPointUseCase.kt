package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class GetNowSlotPayPointUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(): LiveData<Int> {
        return try {
            slotRepository.getNowSlotLive().map {
                it?.payPoint ?: run {
                    0
                }
            }
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.SLOT.groupCode,
                location = "GetNowSlotPayPointUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            MutableLiveData(0)
        }
    }
}