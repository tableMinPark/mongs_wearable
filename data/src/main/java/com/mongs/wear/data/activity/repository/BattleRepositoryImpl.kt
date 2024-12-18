package com.mongs.wear.data.activity.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.core.enums.MatchRoundCode
import com.mongs.wear.data.activity.api.BattleApi
import com.mongs.wear.data.activity.exception.InvalidCreateMatchException
import com.mongs.wear.data.activity.exception.InvalidDeleteMatchException
import com.mongs.wear.data.activity.exception.InvalidEnterMatchException
import com.mongs.wear.data.activity.exception.InvalidExitMatchException
import com.mongs.wear.data.activity.exception.InvalidPickMatchException
import com.mongs.wear.data.activity.exception.InvalidUpdateOverMatchException
import com.mongs.wear.data.activity.exception.NotExistsMatchException
import com.mongs.wear.data.activity.exception.NotExistsMatchPlayerException
import com.mongs.wear.data.common.datastore.AppDataStore
import com.mongs.wear.data.common.exception.InvalidPubMqttException
import com.mongs.wear.data.common.room.RoomDB
import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.battle.model.MatchModel
import com.mongs.wear.domain.battle.model.MatchPlayerModel
import com.mongs.wear.domain.battle.repository.BattleRepository
import javax.inject.Inject

class BattleRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val battleApi: BattleApi,
    private val mqttClient: MqttClient,
    private val appDataStore: AppDataStore,
): BattleRepository {

    override suspend fun getMatchLive(): LiveData<MatchModel> {

        val deviceId = appDataStore.getDeviceId()

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
            throw InvalidCreateMatchException(mongId = mongId)
        }
    }

    override suspend fun deleteMatching(mongId: Long) {

        val response = battleApi.deleteWaitMatching(mongId = mongId)

        if (!response.isSuccessful) {
            throw InvalidDeleteMatchException(mongId = mongId)
        }
    }

    override suspend fun updateOverMatch(roomId: Long) {

        val response = battleApi.overBattle(roomId = roomId)

        if (response.isSuccessful) {

            response.body()?.let {

            }
        }

        throw InvalidUpdateOverMatchException(roomId = roomId)
    }

    override suspend fun enterMatch(roomId: Long) {

        roomDB.matchPlayerDao().findPlayerIdByIsMeTrue()?.let { playerId ->

            try {
                mqttClient.pubBattleMatchEnter(roomId = roomId, playerId = playerId)

            } catch (e: InvalidPubMqttException) {

                throw InvalidEnterMatchException(roomId = roomId, playerId = playerId)
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

            } catch (e: InvalidPubMqttException) {

                throw InvalidPickMatchException(
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

            } catch (e: InvalidPubMqttException) {

                throw InvalidExitMatchException(roomId = roomId, playerId = playerId)
            }
        }
    }
}