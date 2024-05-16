package com.paymong.wear.domain.refac.usecase.auth

import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.refac.repositroy.*

class LoginUseCase(
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
            val accountId = authRepository.login(deviceId = deviceId, email = email!!, name = name!!)

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