package com.mongs.wear.domain.auth.usecase

import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.auth.exception.NotExistsEmailException
import com.mongs.wear.domain.auth.exception.NotExistsNameException
import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String?, name: String?) {

        if (email.isNullOrEmpty()) throw NotExistsEmailException()

        if (name.isNullOrEmpty()) throw NotExistsNameException()

        val deviceId = appRepository.getDeviceId()

        val accountId = authRepository.login(deviceId = deviceId, email = email, name = name)

        mqttClient.subPlayer(accountId = accountId)
    }
}