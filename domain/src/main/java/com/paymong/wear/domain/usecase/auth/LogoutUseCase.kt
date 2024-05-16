package com.paymong.wear.domain.usecase.auth

import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.repositroy.AuthRepository
import com.paymong.wear.domain.repositroy.DeviceRepository
import com.paymong.wear.domain.repositroy.FeedbackRepository
import com.paymong.wear.domain.repositroy.RealTimeRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val deviceRepository: DeviceRepository,
    private val realTimeRepository: RealTimeRepository,
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke() {
        try {
            val refreshToken = deviceRepository.getRefreshToken();

            authRepository.logout(refreshToken = refreshToken)
            realTimeRepository.disconnect()
            realTimeRepository.resetConnection()

        } catch (e: ErrorException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.AUTH.groupCode,
                location = "LogoutUseCase",
                message = e.errorCode.message(),
            )
        }
    }
}