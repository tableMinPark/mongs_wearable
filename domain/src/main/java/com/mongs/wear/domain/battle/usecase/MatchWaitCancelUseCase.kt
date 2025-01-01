package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.battle.repository.BattleRepository
import com.mongs.wear.domain.management.repository.SlotRepository
import javax.inject.Inject

class MatchWaitCancelUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
//        val slotModel = slotRepository.getNowSlot()
//
//        battleRepository.matchWaitCancel(mongId = slotModel.mongId)
//        mqttClient.disSubSearchMatch()
    }
}