package com.mongs.wear.data_.activity.client

import com.mongs.wear.domain.client.BattleMqttClient
import com.mongs.wear.domain.code.BattlePickCode
import javax.inject.Inject

class BattleMqttClientImpl @Inject constructor(

) : BattleMqttClient {

    override suspend fun setConnection(
        matchFindCallback: suspend () -> Unit,
        matchEnterCallback: suspend () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun disconnect() {
        TODO("Not yet implemented")
    }

    override suspend fun subScribeBattleSearch(deviceId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun disSubScribeBattleSearch() {
        TODO("Not yet implemented")
    }

    override suspend fun subScribeBattleMatch(roomId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun disSubScribeBattleMatch() {
        TODO("Not yet implemented")
    }

    override suspend fun produceBattleMatchEnter(roomId: String, playerId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun produceBattleMatchPick(
        roomId: String,
        playerId: String,
        targetPlayerId: String,
        pickCode: BattlePickCode
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun produceBattleMatchExit(roomId: String, playerId: String) {
        TODO("Not yet implemented")
    }
}