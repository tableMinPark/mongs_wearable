package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.BattleRepository
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