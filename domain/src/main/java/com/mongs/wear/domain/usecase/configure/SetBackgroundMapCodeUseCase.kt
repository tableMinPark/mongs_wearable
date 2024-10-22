package com.mongs.wear.domain.usecase.configure

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class SetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke() {
        try {
            deviceRepository.setBackgroundMapCode(code = "MP000")
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.CONFIGURE.groupCode,
                location = "SetBackgroundMapCodeUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
    suspend operator fun invoke(code: String) {
        try {
            deviceRepository.setBackgroundMapCode(code = code)
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.CONFIGURE.groupCode,
                location = "SetBackgroundMapCodeUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}