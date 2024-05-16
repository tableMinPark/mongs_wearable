package com.paymong.wear.domain.refac.usecase.auth

import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.refac.repositroy.AuthRepository
import com.paymong.wear.domain.refac.repositroy.FeedbackRepository
import com.paymong.wear.domain.refac.repositroy.RealTimeRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
    private val realTimeRepository: RealTimeRepository,
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke() {
        try {
            authRepository.logout()
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