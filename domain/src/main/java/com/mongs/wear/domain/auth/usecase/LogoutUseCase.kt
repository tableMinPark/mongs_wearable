package com.mongs.wear.domain.auth.usecase

import com.mongs.wear.domain.auth.repository.AuthRepository
import com.mongs.wear.domain.common.client.MqttClient
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {

        authRepository.logout()

        mqttClient.disSubManager()

        mqttClient.disSubPlayer()

        mqttClient.disSubBattleMatch()

        mqttClient.disSubSearchMatch()
    }
}