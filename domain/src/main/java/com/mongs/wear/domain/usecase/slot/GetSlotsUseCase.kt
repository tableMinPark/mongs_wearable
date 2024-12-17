package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(): LiveData<List<SlotVo>> {
        return slotRepository.getSlots(
            subScribeMong = { mqttClient.subManager(it) },
        )
    }
}