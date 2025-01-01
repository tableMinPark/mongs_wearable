package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.management.repository.ManagementRepository
import javax.inject.Inject

class GraduateMongUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        managementRepository.graduateMong(mongId = mongId)

        mqttClient.disSubManager()
    }
}