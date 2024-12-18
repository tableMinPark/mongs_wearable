package com.mongs.wear.domain.slot.usecase

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.slot.repository.SlotRepository
import com.mongs.wear.domain.slot.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(): LiveData<List<SlotVo>> {
        return slotRepository.getSlotsLive()
    }
}