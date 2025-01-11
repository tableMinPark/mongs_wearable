package com.mongs.wear.domain.device.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.device.exception.ConnectMqttException
import com.mongs.wear.domain.device.repository.DeviceRepository
import com.mongs.wear.domain.global.client.MqttClient
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConnectMqttUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val deviceRepository: DeviceRepository,
) : BaseNoParamUseCase<Unit>() {

    override suspend fun execute() {
        withContext(Dispatchers.IO) {

            mqttClient.connect()

            deviceRepository.setNetwork(network = true)
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw ConnectMqttException()
        }
    }
}