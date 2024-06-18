package com.mongs.wear.domain.usecase.auth

import com.mongs.wear.domain.client.MqttEventClient
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.AuthRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val mqttEventClient: MqttEventClient,
    private val authRepository: AuthRepository,
    private val memberRepository: MemberRepository,
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke() {
        try {
            val refreshToken = memberRepository.getRefreshToken()
            authRepository.logout(refreshToken = refreshToken)

            mqttEventClient.disSubScribeMember()
            mqttEventClient.disSubScribeMong()
            mqttEventClient.resetConnection()
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.AUTH.groupCode,
                location = "LogoutUseCase",
                message = e.errorCode.message(),
            )

            throw UseCaseException(e.errorCode)
        }
    }
}