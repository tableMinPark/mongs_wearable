package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.domain.repositroy.BattleRepository
import javax.inject.Inject

class MatchPickUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(pickCode: MatchRoundCode) {
        val matchVo = battleRepository.getMatch()
        val myMatchPlayerVo = battleRepository.getMyMatchPlayer()
        val otherMatchPlayerVo = battleRepository.getOtherMatchPlayer()
        battleRepository.matchPick(roomId = matchVo.roomId)
        when (pickCode) {
            MatchRoundCode.MATCH_PICK_ATTACK -> {
                mqttClient.pubBattleMatchPick(
                    roomId = matchVo.roomId,
                    playerId = myMatchPlayerVo.playerId,
                    targetPlayerId = otherMatchPlayerVo.playerId,
                    pickCode = pickCode,
                )
            }
            else -> {
                mqttClient.pubBattleMatchPick(
                    roomId = matchVo.roomId,
                    playerId = myMatchPlayerVo.playerId,
                    targetPlayerId = myMatchPlayerVo.playerId,
                    pickCode = pickCode,
                )
            }
        }
    }
}