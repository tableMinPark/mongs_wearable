package com.mongs.wear.domain.auth.usecase

import com.mongs.wear.domain.auth.exception.NotExistsEmailException
import com.mongs.wear.domain.auth.exception.NotExistsGoogleAccountIdException
import com.mongs.wear.domain.auth.repository.AuthRepository
import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.domain.management.repository.SlotRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val authRepository: AuthRepository,
    private val slotRepository: SlotRepository,
) {

    suspend operator fun invoke(googleAccountId: String?, email: String?) {

        if (email.isNullOrEmpty()) throw NotExistsEmailException()

        if (googleAccountId.isNullOrEmpty()) throw NotExistsGoogleAccountIdException()

        val deviceId = appRepository.getDeviceId()

        val accountId = authRepository.login(
            deviceId = deviceId,
            email = email,
            googleAccountId = googleAccountId
        )

        while (mqttClient.isConnectPending()) delay(1000)

        if (mqttClient.isConnected()) {

            mqttClient.subPlayer(accountId = accountId)

            slotRepository.getCurrentSlot()?.let { mongModel ->
                mqttClient.subManager(mongId = mongModel.mongId)
            }

        } else {
            appRepository.setNetwork(network = false)
        }
    }
}