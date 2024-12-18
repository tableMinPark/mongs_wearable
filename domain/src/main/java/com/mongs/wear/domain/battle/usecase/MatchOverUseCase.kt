package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.battle.repository.BattleRepository
import javax.inject.Inject

class MatchOverUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        val matchVo = battleRepository.getMatch()
        battleRepository.matchOver(matchVo.roomId)

        mqttClient.disSubBattleMatch()
    }
}