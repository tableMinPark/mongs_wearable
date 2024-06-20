package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mongs.wear.domain.client.MqttEventClient
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val mqttEventClient: MqttEventClient,
    private val slotRepository: SlotRepository
) {
    suspend operator fun invoke(): LiveData<List<SlotVo>> {
        return try {
            slotRepository.getSlots(
                subScribeMong = { mqttEventClient.subScribeMong(it) },
            )
        } catch (e: RepositoryException) {
            MutableLiveData(ArrayList())
        }
    }
}