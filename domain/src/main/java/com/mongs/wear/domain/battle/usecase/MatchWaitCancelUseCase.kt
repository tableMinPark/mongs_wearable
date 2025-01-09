package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.battle.exception.MatchWaitCancelException
import com.mongs.wear.domain.battle.repository.BattleRepository
import com.mongs.wear.domain.global.client.MqttClient
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import com.mongs.wear.domain.management.repository.SlotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchWaitCancelUseCase @Inject constructor(
    private val mqttClient: MqttClient,
    private val slotRepository: SlotRepository,
    private val battleRepository: BattleRepository,
) : BaseNoParamUseCase<Unit>() {

    override suspend fun execute() {

        withContext(Dispatchers.IO) {

//        val slotModel = slotRepository.getNowSlot()
//
//        battleRepository.matchWaitCancel(mongId = slotModel.mongId)
//        mqttClient.disSubSearchMatch()
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw MatchWaitCancelException()
        }
    }
}