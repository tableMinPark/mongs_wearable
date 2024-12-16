package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.BattleMqttClient
import com.mongs.wear.domain.repositroy.BattleRepository
import javax.inject.Inject

class MatchExitUseCase @Inject constructor(
    private val battleMqttClient: BattleMqttClient,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        val matchVo = battleRepository.getMatch()
        val myMatchPlayerVo = battleRepository.getMyMatchPlayer()

        battleMqttClient.produceBattleMatchExit(
            roomId = matchVo.roomId,
            playerId = myMatchPlayerVo.playerId,
        )
        battleMqttClient.disSubScribeBattleMatch()
        battleMqttClient.disconnect()
    }
}