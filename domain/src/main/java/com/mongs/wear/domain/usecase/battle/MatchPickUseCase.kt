package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.code.BattlePickCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import javax.inject.Inject

class MatchPickUseCase @Inject constructor(
    private val mqttBattleClient: MqttBattleClient,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(pickCode: BattlePickCode) {
        try {
            val matchVo = battleRepository.getMatch()
            val myMatchPlayerVo = battleRepository.getMyMatchPlayer()
            val otherMatchPlayerVo = battleRepository.getOtherMatchPlayer()

            when (pickCode) {
                BattlePickCode.ATTACK -> {
                    mqttBattleClient.produceBattleMatchPick(
                        roomId = matchVo.roomId,
                        playerId = myMatchPlayerVo.playerId,
                        targetPlayerId = otherMatchPlayerVo.playerId,
                        pickCode = pickCode,
                    )
                }
                else -> {
                    mqttBattleClient.produceBattleMatchPick(
                        roomId = matchVo.roomId,
                        playerId = myMatchPlayerVo.playerId,
                        targetPlayerId = myMatchPlayerVo.playerId,
                        pickCode = pickCode,
                    )
                }
            }
            battleRepository.matchPick(roomId = matchVo.roomId)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}