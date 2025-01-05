package com.mongs.wear.domain.auth.usecase

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.exception.LoginException
import com.mongs.wear.domain.auth.exception.NeedJoinException
import com.mongs.wear.domain.auth.exception.NeedUpdateAppException
import com.mongs.wear.domain.auth.exception.NotExistsEmailException
import com.mongs.wear.domain.auth.exception.NotExistsGoogleAccountIdException
import com.mongs.wear.domain.auth.repository.AuthRepository
import com.mongs.wear.domain.device.repository.DeviceRepository
import com.mongs.wear.domain.global.client.MqttClient
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val deviceRepository: DeviceRepository,
    private val authRepository: AuthRepository,
) : BaseParamUseCase<LoginUseCase.Param, Unit>() {

    companion object {
        private const val MQTT_CONNECT_PENDING_CHECK_DELAY = 500L
    }

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {

            if (param.email.isNullOrEmpty()) throw NotExistsEmailException()

            if (param.googleAccountId.isNullOrEmpty()) throw NotExistsGoogleAccountIdException()

            val deviceId = deviceRepository.getDeviceId()

            val accountId = authRepository.login(
                deviceId = deviceId,
                email = param.email,
                googleAccountId = param.googleAccountId
            )

            // 재 로그인 시 ResumeConnect 완료 후 연결을 위해 대기
            while (mqttClient.isConnectPending() && !mqttClient.isConnected()) {
                delay(MQTT_CONNECT_PENDING_CHECK_DELAY)
            }

            if (mqttClient.isConnected()) {
                // 회원 정보 구독
                mqttClient.subPlayer(accountId = accountId)
            } else {
                deviceRepository.setNetwork(network = false)
            }
        }
    }

    data class Param(

        val googleAccountId: String?,

        val email: String?
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {

            DataErrorCode.DATA_AUTH_NEED_JOIN -> throw NeedJoinException()

            DataErrorCode.DATA_AUTH_NEED_UPDATE_APP -> throw NeedUpdateAppException()

            else -> throw LoginException()
        }
    }
}