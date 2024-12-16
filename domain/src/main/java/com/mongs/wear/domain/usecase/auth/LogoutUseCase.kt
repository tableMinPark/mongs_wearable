package com.mongs.wear.domain.usecase.auth

import com.mongs.wear.domain.client.BattleMqttClient
import com.mongs.wear.domain.client.ManagementMqttClient
import com.mongs.wear.domain.repositroy.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val battleMqttClient: BattleMqttClient,
    private val managementMqttClient: ManagementMqttClient,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {

        authRepository.logout()

        managementMqttClient.disSubScribeMember()
        managementMqttClient.disSubScribeMong()
        managementMqttClient.disconnect()

        battleMqttClient.disconnect()
    }
}