package com.mongs.wear.domain.auth.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.exception.InvalidLogoutException
import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {

        try {

            authRepository.logout()

            mqttClient.disSubManager()

            mqttClient.disSubPlayer()

            mqttClient.disSubBattleMatch()

            mqttClient.disSubSearchMatch()

        } catch (_: ErrorException) {

            throw InvalidLogoutException()
        }
    }
}