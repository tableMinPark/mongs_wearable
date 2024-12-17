package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.BattleRepository
import javax.inject.Inject

class MatchExitUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        val matchVo = battleRepository.getMatch()
        val myMatchPlayerVo = battleRepository.getMyMatchPlayer()

        mqttClient.pubBattleMatchExit(
            roomId = matchVo.roomId,
            playerId = myMatchPlayerVo.playerId
        )
        mqttClient.disSubBattleMatch()
    }
}