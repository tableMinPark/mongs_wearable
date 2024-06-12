package com.mongs.wear.data.callback

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.data.api.code.PublishCode
import com.mongs.wear.data.dataStore.BattleDataStore
import com.mongs.wear.data.utils.GsonDateFormatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.Exception
import java.time.LocalDateTime
import com.mongs.wear.data.dto.mqtt.res.BasicPublish
import com.mongs.wear.data.dto.mqttBattle.res.MatchFindVo


class BattleMessageCallback (
    private val battleDataStore: BattleDataStore
) : MqttCallback {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        message?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val json = it.toString()

                    Log.i("MessageCallback", "[$topic] $json")

                    topic?.let { topicName ->
                        if (topicName.startsWith("mongs/battle/match/search")) {
                            val body = gson.fromJson(json, BasicPublish::class.java)
                            val dataJson = gson.toJson(body.data)
                            when (body.code) {
                                PublishCode.MATCH_FIND -> {
                                    val data: MatchFindVo = gson.fromJson(dataJson, MatchFindVo::class.java)
                                    battleDataStore.setRoomId(roomId = data.roomId)
                                    battleDataStore.setIsMatched(isMatched = true)
                                }
                                else -> {}
                            }
                        } else if (topicName.startsWith("mongs/battle/match/round")) {
                            val body = gson.fromJson(json, BasicPublish::class.java)
                            val dataJson = gson.toJson(body.data)
                            when (body.code) {
                                PublishCode.MATCH_START -> {
                                    // TODO("MATCH_START")
                                }
                                PublishCode.ROUND_OVER -> {
                                    // TODO("ROUND_OVER")
                                }
                                PublishCode.MATCH_OVER -> {
                                    // TODO("MATCH_OVER")
                                }
                                else -> {}
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