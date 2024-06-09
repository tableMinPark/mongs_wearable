package com.mongs.wear.data.callback

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.data.api.code.PublishCode
import com.mongs.wear.data.repository.RealTimeRepositoryImpl
import com.mongs.wear.data.utils.GsonDateFormatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.Exception
import java.time.LocalDateTime
import com.google.gson.reflect.TypeToken
import com.mongs.wear.data.dto.mqtt.res.BasicPublish
import com.mongs.wear.data.dto.mqtt.res.MongExpVo
import java.lang.reflect.Type


class MessageCallback (
    private val realTimeRepositoryImpl: RealTimeRepositoryImpl
) : MqttCallback {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    private val listType: Type = object : TypeToken<List<BasicPublish<Any>>>() {}.type


    override fun messageArrived(topic: String?, message: MqttMessage?) {
        message?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val json = it.toString()

                    Log.i("MessageCallback", "[] $json")
                    val body: List<BasicPublish<Any>> = gson.fromJson(json, listType)

                    body.sortedByDescending { it.createdAt }
                        .distinctBy { it.code }
                        .forEach {
                            val dataJson = gson.toJson(it.data)
                            when (it.code) {
                                PublishCode.MEMBER_STAR_POINT -> {
                                    realTimeRepositoryImpl.memberStarPointCallback(dataJson)
                                }
                                PublishCode.MONG_CODE -> {
                                    realTimeRepositoryImpl.mongCodeCallback(dataJson)
                                }
                                PublishCode.MONG_EXP -> {
                                    realTimeRepositoryImpl.mongExpCallback(dataJson)
                                }
                                PublishCode.MONG_IS_SLEEPING -> {
                                    realTimeRepositoryImpl.mongIsSleepingCallback(dataJson)
                                }
                                PublishCode.MONG_PAY_POINT -> {
                                    realTimeRepositoryImpl.mongPayPointCallback(dataJson)
                                }
                                PublishCode.MONG_POOP_COUNT -> {
                                    realTimeRepositoryImpl.mongPoopCountCallback(dataJson)
                                }
                                PublishCode.MONG_SHIFT -> {
                                    realTimeRepositoryImpl.mongShiftCallback(dataJson)
                                }
                                PublishCode.MONG_STATE -> {
                                    realTimeRepositoryImpl.mongStateCallback(dataJson)
                                }
                                PublishCode.MONG_STATUS -> {
                                    realTimeRepositoryImpl.mongStatusCallback(dataJson)
                                }
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