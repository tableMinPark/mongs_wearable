package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.management.repository.SlotRepository
import javax.inject.Inject

class SetCurrentSlotUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        slotRepository.setCurrentSlot(mongId = mongId)

        mqttClient.disSubManager()

        mqttClient.subManager(mongId = mongId)
    }
}