package com.mongs.wear.domain.usecase.slot

import android.util.Log
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.exception.parent.RoomException
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class SetNowSlotUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(mongId: Long) {

        try {
            val slotModel = slotRepository.getNowSlot()
            mqttClient.subScribeMong(pastMongId = slotModel.mongId, mongId = mongId)
        } catch (e: RoomException) {
            Log.d("SetNowSlotUseCase", "Empty Slot List")
            mqttClient.subScribeMong(mongId = mongId)
        }

        slotRepository.setNowSlot(mongId = mongId)
    }
}