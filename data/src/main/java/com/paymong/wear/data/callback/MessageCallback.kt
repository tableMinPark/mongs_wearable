package com.paymong.wear.data.callback

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paymong.wear.data.api.code.PublishCode
import com.paymong.wear.data.dto.mqtt.res.BasicPublish
import com.paymong.wear.data.repository.RealTimeRepositoryImpl
import com.paymong.wear.data.utils.GsonDateFormatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.Exception
import java.time.LocalDateTime


class MessageCallback (
    private val realTimeRepositoryImpl: RealTimeRepositoryImpl
) : MqttCallback {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        message?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val json = it.toString()
                    val response = gson.fromJson(json, BasicPublish::class.java)

                    Log.i("MessageCallback", "[${response.code}] $json")

                    when (response.code) {
                        PublishCode.MEMBER_STAR_POINT -> {
                            realTimeRepositoryImpl.memberStarPointCallback(json)
                        }
                        PublishCode.MONG_CODE -> {
                            realTimeRepositoryImpl.mongCodeCallback(json)
                        }
                        PublishCode.MONG_EXP -> {
                            realTimeRepositoryImpl.mongExpCallback(json)
                        }
                        PublishCode.MONG_IS_SLEEPING -> {
                            realTimeRepositoryImpl.mongIsSleepingCallback(json)
                        }
                        PublishCode.MONG_PAY_POINT -> {
                            realTimeRepositoryImpl.mongPayPointCallback(json)
                        }
                        PublishCode.MONG_POOP_COUNT -> {
                            realTimeRepositoryImpl.mongPoopCountCallback(json)
                        }
                        PublishCode.MONG_SHIFT -> {
                            realTimeRepositoryImpl.mongShiftCallback(json)
                        }
                        PublishCode.MONG_STATE -> {
                            realTimeRepositoryImpl.mongStateCallback(json)
                        }
                        PublishCode.MONG_STATUS -> {
                            realTimeRepositoryImpl.mongStatusCallback(json)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
    override fun connectionLost(cause: Throwable?) {}
}