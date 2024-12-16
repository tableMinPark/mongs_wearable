package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.client.ManagementMqttClient
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class SetNowSlotUseCase @Inject constructor(
    private val managementMqttClient: ManagementMqttClient,
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        slotRepository.setNowSlot(
            subScribeMong = { managementMqttClient.subScribeMong(it) },
            mongId = mongId
        )
        slotRepository.syncNowSlot()
    }
}