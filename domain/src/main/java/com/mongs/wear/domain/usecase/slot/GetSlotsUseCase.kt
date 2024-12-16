package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.client.ManagementMqttClient
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val managementMqttClient: ManagementMqttClient,
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(): LiveData<List<SlotVo>> {
        return slotRepository.getSlots(
            subScribeMong = { managementMqttClient.subScribeMong(it) },
        )
    }
}