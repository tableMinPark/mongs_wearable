package com.paymong.wear.data.client

import android.util.Log
import com.paymong.wear.data.api.client.MqttApi
import com.paymong.wear.data.callback.MessageCallback
import com.paymong.wear.data.repository.RealTimeRepositoryImpl
import com.paymong.wear.domain.client.MqttClient
import javax.inject.Inject

class MqttClientImpl @Inject constructor(
    private val realTimeRepositoryImpl: RealTimeRepositoryImpl,
    private val mqttApi: MqttApi,
    ): MqttClient {

    companion object {
        const val MEMBER_TOPIC = "mongs/member"
        const val MONG_TOPIC = "mongs/mong"
    }

    override suspend fun setConnection(accountId: Long) {
        mqttApi.init(
            messageCallback = MessageCallback(realTimeRepositoryImpl = realTimeRepositoryImpl),
            connectDisableCallback = {
                Log.e("MqttApi", "mqtt connect fail.")
            },
            connectSuccessCallback = {
                Log.i("MqttApi", "mqtt connect.")
                mqttApi.subscribe(
                    topic = "$MEMBER_TOPIC/$accountId",
                    mqttActionListener = SubscribeCallback(disconnect = { mqttApi.disConnect() })
                )
            }
        )
    }
    override suspend fun reconnect() {
        mqttApi.connect()
    }
    override suspend fun disconnect() {
        mqttApi.disConnect()
    }
    override suspend fun resetConnection() {
        mqttApi.reset()
    }
    override suspend fun subScribeMong(pastMongId: Long, mongId: Long) {
        if (pastMongId > -1L) {
            mqttApi.disSubscribe(topic = "$MONG_TOPIC/$mongId")
        }
        mqttApi.subscribe(
            topic = "$MONG_TOPIC/$mongId",
            mqttActionListener = SubscribeCallback(disconnect = { mqttApi.disConnect() })
        )
    }
}