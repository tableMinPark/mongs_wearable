package com.mongs.wear.domain.usecase.auth

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.exception.InvalidLoginException
import com.mongs.wear.domain.repositroy.AppRepository
import com.mongs.wear.domain.repositroy.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String?, name: String?) {

        if (email.isNullOrEmpty() || name.isNullOrEmpty()) throw InvalidLoginException()

        val deviceId = appRepository.getDeviceId()
        val accountId = authRepository.login(deviceId = deviceId, email = email, name = name)

        mqttClient.subPlayer(accountId = accountId)
    }
}