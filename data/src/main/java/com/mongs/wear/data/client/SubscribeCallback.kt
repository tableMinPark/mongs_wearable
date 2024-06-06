package com.mongs.wear.data.client

import android.util.Log
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

class SubscribeCallback(
    val disconnect: () -> Unit
) : IMqttActionListener {

    override fun onSuccess(asyncActionToken: IMqttToken?) {
        asyncActionToken?.let {
            Log.i("SubscribeCallback", "[${it.topics[0]}] subscribe.")
        }
    }
    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        asyncActionToken?.let {
            Log.i("SubscribeCallback", "[${it.topics[0]}] subscribe fail.")
            disconnect()
        }
    }
}