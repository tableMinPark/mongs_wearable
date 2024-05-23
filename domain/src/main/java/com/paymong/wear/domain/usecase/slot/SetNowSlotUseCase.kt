package com.paymong.wear.domain.usecase.slot

import android.util.Log
import com.paymong.wear.domain.client.MqttClient
import com.paymong.wear.domain.exception.RoomException
import com.paymong.wear.domain.repositroy.SlotRepository
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