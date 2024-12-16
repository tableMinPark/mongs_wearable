package com.mongs.wear.data_.manager.client

import com.mongs.wear.domain.client.ManagementMqttClient
import javax.inject.Inject

class ManagementMqttClientImpl @Inject constructor(

) : ManagementMqttClient {

    override suspend fun setConnection() {
        TODO("Not yet implemented")
    }

    override suspend fun reconnect(resetMember: () -> Unit, resetSlot: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun pauseConnect() {
        TODO("Not yet implemented")
    }

    override suspend fun disconnect() {
        TODO("Not yet implemented")
    }

    override suspend fun subScribeMember(accountId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun disSubScribeMember() {
        TODO("Not yet implemented")
    }

    override suspend fun subScribeMong(mongId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun disSubScribeMong() {
        TODO("Not yet implemented")
    }
}