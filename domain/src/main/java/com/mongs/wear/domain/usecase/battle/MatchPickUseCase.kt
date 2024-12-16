package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.BattleMqttClient
import com.mongs.wear.domain.code.BattlePickCode
import com.mongs.wear.domain.repositroy.BattleRepository
import javax.inject.Inject

class MatchPickUseCase @Inject constructor(
    private val battleMqttClient: BattleMqttClient,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(pickCode: BattlePickCode) {
        val matchVo = battleRepository.getMatch()
        val myMatchPlayerVo = battleRepository.getMyMatchPlayer()
        val otherMatchPlayerVo = battleRepository.getOtherMatchPlayer()
        battleRepository.matchPick(roomId = matchVo.roomId)
        when (pickCode) {
            BattlePickCode.ATTACK -> {
                battleMqttClient.produceBattleMatchPick(
                    roomId = matchVo.roomId,
                    playerId = myMatchPlayerVo.playerId,
                    targetPlayerId = otherMatchPlayerVo.playerId,
                    pickCode = pickCode,
                )
            }
            else -> {
                battleMqttClient.produceBattleMatchPick(
                    roomId = matchVo.roomId,
                    playerId = myMatchPlayerVo.playerId,
                    targetPlayerId = myMatchPlayerVo.playerId,
                    pickCode = pickCode,
                )
            }
        }
    }
}