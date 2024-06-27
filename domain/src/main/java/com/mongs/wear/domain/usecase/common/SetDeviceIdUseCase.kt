package com.mongs.wear.domain.usecase.common

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import java.util.UUID
import javax.inject.Inject

class SetDeviceIdUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val feedbackRepository: FeedbackRepository,
){
    suspend operator fun invoke() {
        try {
            if (deviceRepository.getDeviceId().isBlank()) {
                val newDeviceId = UUID.randomUUID().toString()
                deviceRepository.setDeviceId(deviceId = newDeviceId)
            }
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "GetMapCollectionsUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}