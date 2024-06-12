package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import com.mongs.wear.data.api.code.PublishCode
import com.mongs.wear.data.dataStore.BattleDataStore
import com.mongs.wear.data.dto.mqtt.res.BasicPublish
import com.mongs.wear.data.dto.mqttBattle.res.MatchExitVo
import com.mongs.wear.data.dto.mqttBattle.res.MatchSearchVo
import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.vo.BattleRoomVo
import javax.inject.Inject

class BattleRepositoryImpl @Inject constructor(
    private val battleDataStore: BattleDataStore,
    private val mqttBattleClient: MqttBattleClient
): BattleRepository {

    override suspend fun getBattleRoom(): LiveData<BattleRoomVo> {
        return battleDataStore.getBattleRoomLive()
    }

    override suspend fun matchSearch(mongId: Long) {
        mqttBattleClient.setConnection()
        mqttBattleClient.subScribeBattleMatch(mongId = mongId)
        mqttBattleClient.produceBattleMatch(
            mongId = mongId,
            data = BasicPublish(
                code = PublishCode.MATCH_SEARCH,
                data = MatchSearchVo(
                    mongId = mongId
                )
            )
        )
    }

    override suspend fun roundPick(direction: String) {
        TODO("Not yet implemented")
    }

    override suspend fun matchExit(mongId: Long) {
        val roomId = battleDataStore.getRoomId()
        mqttBattleClient.produceBattleRound(
            roomId = roomId,
            data = BasicPublish(
                code = PublishCode.MATCH_EXIT,
                data = MatchExitVo(
                    mongId = mongId,
                    roomId = roomId,
                )
            )
        )
        mqttBattleClient.disconnect()
    }
}