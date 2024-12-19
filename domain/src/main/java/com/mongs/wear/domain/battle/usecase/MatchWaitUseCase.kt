package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.domain.battle.repository.BattleRepository
import com.mongs.wear.domain.slot.repository.SlotRepository
import javax.inject.Inject

class MatchWaitUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
    private val appRepository: AppRepository,
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(
        matchFindCallback: suspend () -> Unit,
        matchEnterCallback: suspend () -> Unit,
    ) {

//        val slowModel = slotRepository.getNowSlot()
//        val deviceId = appRepository.getDeviceId()
//
//        mqttClient.subSearchMatch(deviceId = deviceId)
//        battleRepository.matchWait(mongId = slowModel.mongId)
    }
}