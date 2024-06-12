package com.mongs.wear.domain.usecase.auth

import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.error.UseCaseErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.AuthRepository
import com.mongs.wear.domain.repositroy.CodeRepository
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import com.mongs.wear.domain.repositroy.SlotRepository
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
                throw UseCaseException(UseCaseErrorCode.MUST_UPDATE_APP)
            } else if(versionModel.updateCode) {
                codeRepository.setCodes(codeIntegrity = codeIntegrity, buildVersion = buildVersion)
            }

            mqttClient.setConnection(accountId = accountId)
            mqttClient.subScribeMember(accountId = accountId)

            memberRepository.setMember()
            slotRepository.setSlots()

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