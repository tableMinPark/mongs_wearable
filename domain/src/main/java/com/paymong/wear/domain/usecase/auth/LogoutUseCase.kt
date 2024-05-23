package com.paymong.wear.domain.usecase.auth

import com.paymong.wear.domain.client.MqttClient
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.repositroy.AuthRepository
import com.paymong.wear.domain.repositroy.FeedbackRepository
import com.paymong.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val authRepository: AuthRepository,
    private val memberRepository: MemberRepository,
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke() {
        try {
            val refreshToken = memberRepository.getRefreshToken();

            authRepository.logout(refreshToken = refreshToken)
            mqttClient.disconnect()
            mqttClient.resetConnection()

        } catch (e: ErrorException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.AUTH.groupCode,
                location = "LogoutUseCase",
                message = e.errorCode.message(),
            )
        }
    }
}