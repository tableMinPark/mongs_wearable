package com.mongs.wear.domain.usecase.auth

import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.AuthRepository
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