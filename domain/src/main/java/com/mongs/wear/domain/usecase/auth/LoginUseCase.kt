package com.mongs.wear.domain.usecase.auth

import com.mongs.wear.domain.client.ManagementMqttClient
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.error.UseCaseErrorCode
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.AppRepository
import com.mongs.wear.domain.repositroy.AuthRepository
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.PlayerRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val managementMqttClient: ManagementMqttClient,

    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val authRepository: AuthRepository,
    private val playerRepository: PlayerRepository,
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(email: String, name: String) {

        val deviceId = appRepository.getDeviceId()
        val accountId = authRepository.login(deviceId = deviceId, email = email, name = name)

        mqttClient.subPlayer(accountId = accountId)

        playerRepository.setMember()
        slotRepository.setSlots(subScribeMong = { managementMqttClient.subScribeMong(mongId = it) })
    }
}