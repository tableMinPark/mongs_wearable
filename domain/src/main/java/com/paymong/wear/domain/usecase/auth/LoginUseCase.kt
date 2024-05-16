package com.paymong.wear.domain.usecase.auth

import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.repositroy.AuthRepository
import com.paymong.wear.domain.repositroy.CodeRepository
import com.paymong.wear.domain.repositroy.DeviceRepository
import com.paymong.wear.domain.repositroy.FeedbackRepository
import com.paymong.wear.domain.repositroy.MemberRepository
import com.paymong.wear.domain.repositroy.RealTimeRepository
import com.paymong.wear.domain.repositroy.SlotRepository
import java.util.UUID
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val codeRepository: CodeRepository,
    private val deviceRepository: DeviceRepository,
    private val memberRepository: MemberRepository,
    private val realTimeRepository: RealTimeRepository,
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
            deviceRepository.setAccessToken(accessToken = accessToken)
            deviceRepository.setRefreshToken(refreshToken = refreshToken)

            val codeIntegrity = deviceRepository.getCodeIntegrity()
            val buildVersion = deviceRepository.getBuildVersion()
            codeRepository.setCodes(codeIntegrity = codeIntegrity, buildVersion = buildVersion)

            memberRepository.setMember(accountId = accountId)
            slotRepository.setSlots(accountId = accountId)
            realTimeRepository.setConnection(accountId = accountId)

        } catch (e: ErrorException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.AUTH.groupCode,
                location = "LoginUseCase",
                message = e.errorCode.message(),
            )
        }
    }
}