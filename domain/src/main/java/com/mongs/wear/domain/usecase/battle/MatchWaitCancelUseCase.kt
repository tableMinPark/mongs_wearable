package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class MatchWaitCancelUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        val slotModel = slotRepository.getNowSlot()

        battleRepository.matchWaitCancel(mongId = slotModel.mongId)
        mqttClient.disSubSearchMatch()
    }
}