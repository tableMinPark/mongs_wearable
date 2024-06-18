package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class MatchWaitUseCase @Inject constructor(
    private val mqttBattleClient: MqttBattleClient,
    private val deviceRepository: DeviceRepository,
    private val slotRepository: SlotRepository,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        try {
            val slotModel = slotRepository.getNowSlot()
            val deviceId = deviceRepository.getDeviceId()

            mqttBattleClient.setConnection()
            mqttBattleClient.subScribeBattleSearch(deviceId = deviceId)
            battleRepository.matchWait(mongId = slotModel.mongId)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}