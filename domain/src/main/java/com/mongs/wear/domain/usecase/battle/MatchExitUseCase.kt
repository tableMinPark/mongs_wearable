package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.ClientException
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class MatchExitUseCase @Inject constructor(
    private val mqttBattleClient: MqttBattleClient,
    private val battleRepository: BattleRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke() {
        try {
            val matchVo = battleRepository.getMatch()
            val myMatchPlayerVo = battleRepository.getMyMatchPlayer()

            mqttBattleClient.produceBattleMatchExit(
                roomId = matchVo.roomId,
                playerId = myMatchPlayerVo.playerId,
            )
            mqttBattleClient.disSubScribeBattleMatch()
            mqttBattleClient.disconnect()
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "MatchExitUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        } catch (e: ClientException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "MatchExitUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}