package com.mongs.wear.data.activity.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.core.enums.MatchStateCode
import com.mongs.wear.data.activity.api.BattleApi
import com.mongs.wear.data.activity.exception.CreateMatchException
import com.mongs.wear.data.activity.exception.DeleteMatchException
import com.mongs.wear.data.activity.exception.EnterMatchException
import com.mongs.wear.data.activity.exception.ExitMatchException
import com.mongs.wear.data.activity.exception.PickMatchException
import com.mongs.wear.data.activity.exception.UpdateOverMatchException
import com.mongs.wear.data.activity.exception.NotExistsMatchException
import com.mongs.wear.data.activity.exception.NotExistsMatchPlayerException
import com.mongs.wear.data.device.datastore.DeviceDataStore
import com.mongs.wear.data.global.exception.PubMqttException
import com.mongs.wear.data.global.room.RoomDB
import com.mongs.wear.domain.global.client.MqttClient
import com.mongs.wear.domain.battle.model.MatchModel
import com.mongs.wear.domain.battle.model.MatchPlayerModel
import com.mongs.wear.domain.battle.repository.BattleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BattleRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val battleApi: BattleApi,
    private val mqttClient: MqttClient,
    private val deviceDataStore: DeviceDataStore,
): BattleRepository {

    override suspend fun getMatchLive(): LiveData<MatchModel> {

        val deviceId = deviceDataStore.getDeviceId()

        return roomDB.matchRoomDao().findLiveByDeviceId(deviceId = deviceId).map { matchRoomEntity ->
            matchRoomEntity?.let {
                MatchModel(
                    roomId = matchRoomEntity.roomId ?: 0,
                    round = matchRoomEntity.round,
                    isLastRound = matchRoomEntity.isLastRound,
                    stateCode = matchRoomEntity.stateCode,
                )
            } ?: run {
                throw NotExistsMatchException(deviceId = deviceId)
            }
        }
    }

    override suspend fun getMyMatchPlayerLive(): LiveData<MatchPlayerModel> {

        return roomDB.matchPlayerDao().findLiveByPlayerIdIsMeTrue().map { matchPlayerEntity ->
            matchPlayerEntity?.let {
                MatchPlayerModel(
                    playerId = matchPlayerEntity.playerId,
                    mongTypeCode = matchPlayerEntity.mongTypeCode,
                    hp = matchPlayerEntity.hp,
                    roundCode = matchPlayerEntity.roundCode,
                    isMe = matchPlayerEntity.isMe,
                    isWin = matchPlayerEntity.isWin,
                )
            } ?: run {
                throw NotExistsMatchPlayerException()
            }
        }
    }

    override suspend fun getRiverMatchPlayerLive(): LiveData<MatchPlayerModel> {

        return roomDB.matchPlayerDao().findLiveByPlayerIdIsMeFalse().map { matchPlayerEntity ->
            matchPlayerEntity?.let {
                MatchPlayerModel(
                    playerId = matchPlayerEntity.playerId,
                    mongTypeCode = matchPlayerEntity.mongTypeCode,
                    hp = matchPlayerEntity.hp,
                    roundCode = matchPlayerEntity.roundCode,
                    isMe = matchPlayerEntity.isMe,
                    isWin = matchPlayerEntity.isWin,
                )
            } ?: run {
                throw NotExistsMatchPlayerException()
            }
        }
    }

    override suspend fun createMatching(mongId: Long) {

        val response = battleApi.createWaitMatching(mongId = mongId)

        if (!response.isSuccessful) {
            throw CreateMatchException(mongId = mongId)
        }
    }

    override suspend fun deleteMatching(mongId: Long) {

        val response = battleApi.deleteWaitMatching(mongId = mongId)

        if (!response.isSuccessful) {
            throw DeleteMatchException(mongId = mongId)
        }
    }

    override suspend fun updateOverMatch(roomId: Long) {

        mqttClient.disSubBattleMatch()

        val response = battleApi.overBattle(roomId = roomId)

        if (response.isSuccessful) {

            response.body()?.let { body ->

                roomDB.matchRoomDao().let { dao ->
                    dao.findByRoomId(roomId = roomId)?.let { matchRoomEntity ->
                        dao.save(
                            matchRoomEntity.update(
                                isLastRound = true,
                                stateCode = MatchStateCode.MATCH_OVER,
                            )
                        )
                    }
                }

                roomDB.matchPlayerDao().let { dao ->

                    dao.findByPlayerId(playerId = body.result.winPlayerId)?.let { matchPlayerEntity ->
                        dao.save(
                            matchPlayerEntity.update(
                                isWin = true,
                            )
                        )
                    }
                }
            }

        } else {
            throw UpdateOverMatchException(roomId = roomId)
        }
    }

    override suspend fun enterMatch(roomId: Long) {

        roomDB.matchPlayerDao().findPlayerIdByIsMeTrue()?.let { playerId ->

            try {
                mqttClient.pubBattleMatchEnter(roomId = roomId, playerId = playerId)

            } catch (e: PubMqttException) {

                throw EnterMatchException(roomId = roomId, playerId = playerId)
            }
        }
    }

    override suspend fun pickMatch(roomId: Long, targetPlayerId: String, pickCode: MatchRoundCode) {

        roomDB.matchPlayerDao().findPlayerIdByIsMeTrue()?.let { playerId ->

            try {
                mqttClient.pubBattleMatchPick(
                    roomId = roomId,
                    playerId = playerId,
                    targetPlayerId = targetPlayerId,
                    pickCode = pickCode
                )

            } catch (e: PubMqttException) {

                throw PickMatchException(
                    roomId = roomId,
                    playerId = playerId,
                    pickCode = pickCode
                )
            }
        }
    }

    override suspend fun exitMatch(roomId: Long) {

        roomDB.matchPlayerDao().findPlayerIdByIsMeTrue()?.let { playerId ->

            try {
                mqttClient.pubBattleMatchExit(roomId = roomId, playerId = playerId)

                mqttClient.disSubBattleMatch()

            } catch (e: PubMqttException) {

                throw ExitMatchException(roomId = roomId, playerId = playerId)
            }
        }
    }
}