package com.paymong.wear.domain.usecase.auth

import android.util.Log
import com.paymong.wear.domain.client.MqttClient
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.error.UseCaseErrorCode
import com.paymong.wear.domain.exception.child.MustUpdateAppException
import com.paymong.wear.domain.exception.parent.RepositoryException
import com.paymong.wear.domain.exception.parent.UseCaseException
import com.paymong.wear.domain.repositroy.AuthRepository
import com.paymong.wear.domain.repositroy.CodeRepository
import com.paymong.wear.domain.repositroy.DeviceRepository
import com.paymong.wear.domain.repositroy.FeedbackRepository
import com.paymong.wear.domain.repositroy.MemberRepository
import com.paymong.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val authRepository: AuthRepository,
    private val codeRepository: CodeRepository,
    private val deviceRepository: DeviceRepository,
    private val memberRepository: MemberRepository,
    private val slotRepository: SlotRepository,
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(email: String?, name: String?) {
        try {
            val deviceId = deviceRepository.getDeviceId()
            val loginModel = authRepository.login(deviceId = deviceId, email = email!!, name = name!!)

            val accountId = loginModel.accountId
            val accessToken = loginModel.accessToken
            val refreshToken = loginModel.refreshToken
            memberRepository.setAccessToken(accessToken = accessToken)
            memberRepository.setRefreshToken(refreshToken = refreshToken)

            val codeIntegrity = deviceRepository.getCodeIntegrity()
            val buildVersion = deviceRepository.getBuildVersion()

            val versionModel = codeRepository.validationVersion(codeIntegrity = codeIntegrity, buildVersion = buildVersion)
            if (versionModel.updateApp) {
                throw MustUpdateAppException(UseCaseErrorCode.MUST_UPDATE_APP)
            } else if(versionModel.updateCode) {
                codeRepository.setCodes(codeIntegrity = codeIntegrity, buildVersion = buildVersion)
            }

            memberRepository.setMember(accountId = accountId)
            slotRepository.setSlots(accountId = accountId)
            mqttClient.setConnection(accountId = accountId)

        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.AUTH.groupCode,
                location = "LoginUseCase",
                message = e.errorCode.message(),
            )

            throw UseCaseException(e.errorCode)
        }
    }
}