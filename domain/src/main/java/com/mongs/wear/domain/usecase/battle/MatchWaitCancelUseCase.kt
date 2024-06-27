package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.ClientException
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class MatchWaitCancelUseCase @Inject constructor(
    private val mqttBattleClient: MqttBattleClient,
    private val slotRepository: SlotRepository,
    private val battleRepository: BattleRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke() {
        try {
            val slotModel = slotRepository.getNowSlot()

            battleRepository.matchWaitCancel(mongId = slotModel.mongId)
            mqttBattleClient.disSubScribeBattleSearch()
            mqttBattleClient.disconnect()
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "MatchWaitCancelUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        } catch (e: ClientException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "MatchWaitCancelUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}