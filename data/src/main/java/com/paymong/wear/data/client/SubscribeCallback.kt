package com.paymong.wear.data.client

import android.util.Log
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

class SubscribeCallback(
    val disconnect: () -> Unit
) : IMqttActionListener {

    override fun onSuccess(asyncActionToken: IMqttToken?) {
        // 구독 성공 시의 처리
        asyncActionToken?.let {
            Log.d("Matt", "[SUBSCRIBE ${it.topics[0]} SUCCESS]")
        }
    }
    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        // 구독 실패 시의 처리
        asyncActionToken?.let {
            Log.d("Matt", "[SUBSCRIBE ${it.topics} FAIL]")
            disconnect()
        }
    }
}