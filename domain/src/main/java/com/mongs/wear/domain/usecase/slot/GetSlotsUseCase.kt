package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mongs.wear.domain.client.MqttEventClient
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val mqttEventClient: MqttEventClient,
    private val slotRepository: SlotRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(): LiveData<List<SlotVo>> {
        return try {
            slotRepository.getSlots(
                subScribeMong = { mqttEventClient.subScribeMong(it) },
            )
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.SLOT.groupCode,
                location = "GetSlotsUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            MutableLiveData(ArrayList())
        }
    }
}