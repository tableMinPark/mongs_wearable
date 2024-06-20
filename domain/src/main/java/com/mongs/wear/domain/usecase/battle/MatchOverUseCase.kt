package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.code.BattlePickCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import javax.inject.Inject

class MatchOverUseCase @Inject constructor(
    private val mqttBattleClient: MqttBattleClient,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        try {
            val matchVo = battleRepository.getMatch()
            battleRepository.matchOver(matchVo.roomId)

            mqttBattleClient.disSubScribeBattleMatch()
            mqttBattleClient.disconnect()
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}