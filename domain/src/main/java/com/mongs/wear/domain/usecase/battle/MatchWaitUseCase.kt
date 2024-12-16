package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.client.BattleMqttClient
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class MatchWaitUseCase @Inject constructor(
    private val battleMqttClient: BattleMqttClient,
    private val slotRepository: SlotRepository,
    private val deviceRepository: DeviceRepository,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(
        matchFindCallback: suspend () -> Unit,
        matchEnterCallback: suspend () -> Unit,
    ) {
        val slowModel = slotRepository.getNowSlot()
        val deviceId = deviceRepository.getDeviceId()

        battleMqttClient.setConnection(
            matchFindCallback = matchFindCallback,
            matchEnterCallback = matchEnterCallback,
        )
        battleMqttClient.subScribeBattleSearch(deviceId = deviceId)
        battleRepository.matchWait(mongId = slowModel.mongId)
    }
}