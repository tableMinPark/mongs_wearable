package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.BattleMqttClient
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class MatchWaitCancelUseCase @Inject constructor(
    private val battleMqttClient: BattleMqttClient,
    private val slotRepository: SlotRepository,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        val slotModel = slotRepository.getNowSlot()

        battleRepository.matchWaitCancel(mongId = slotModel.mongId)
        battleMqttClient.disSubScribeBattleSearch()
        battleMqttClient.disconnect()
    }
}