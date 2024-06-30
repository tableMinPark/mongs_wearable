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

class MatchWaitUseCase @Inject constructor(
    private val mqttBattleClient: MqttBattleClient,
    private val slotRepository: SlotRepository,
    private val deviceRepository: DeviceRepository,
    private val battleRepository: BattleRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(
        matchFindCallback: suspend () -> Unit,
        matchEnterCallback: suspend () -> Unit,
    ) {
        try {
            val slowModel = slotRepository.getNowSlot()
            val deviceId = deviceRepository.getDeviceId()

            mqttBattleClient.setConnection(
                matchFindCallback = matchFindCallback,
                matchEnterCallback = matchEnterCallback,
            )
            mqttBattleClient.subScribeBattleSearch(deviceId = deviceId)
            battleRepository.matchWait(mongId = slowModel.mongId)
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "MatchWaitUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        } catch (e: ClientException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "MatchWaitUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}