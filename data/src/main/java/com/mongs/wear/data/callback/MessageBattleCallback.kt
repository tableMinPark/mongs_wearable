package com.mongs.wear.data.callback

import android.util.Log
import com.google.gson.Gson
import com.mongs.wear.data.api.code.PublishBattleCode
import com.mongs.wear.data.dto.mqttBattle.BasicBattlePublish
import com.mongs.wear.data.dto.mqttBattle.res.MatchFindVo
import com.mongs.wear.data.dto.mqttBattle.res.MatchOverVo
import com.mongs.wear.data.dto.mqttBattle.res.MatchVo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage


class MessageBattleCallback (
    private val battleSearchBaseTopic: String,
    private val battleMatchBaseTopic: String,
    private val matchFind: suspend (String, String) -> Unit,
    private val matchEnter: suspend (MatchVo) -> Unit,
    private val match: suspend (MatchVo) -> Unit,
    private val matchOver: suspend (MatchOverVo) -> Unit,
    private val gson: Gson,
) : MqttCallback {
    override fun messageArrived(topic: String?, message: MqttMessage?) {
        message?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val json = it.toString()

                    topic?.let { topicName ->
                        if (topicName.startsWith(battleSearchBaseTopic)) {
                            val body = gson.fromJson(json, BasicBattlePublish::class.java)
                            val dataJson = gson.toJson(body.data)
                            when (body.code) {
                                PublishBattleCode.MATCH_FIND -> {
                                    val data: MatchFindVo = gson.fromJson(dataJson, MatchFindVo::class.java)
                                    Log.i("BattleMessageCallback", "$data")
                                    matchFind(data.roomId, data.playerId)
                                }
                                else -> {}
                            }
                        } else if (topicName.startsWith(battleMatchBaseTopic)) {
                            val body = gson.fromJson(json, BasicBattlePublish::class.java)
                            val dataJson = gson.toJson(body.data)
                            when (body.code) {
                                PublishBattleCode.MATCH -> {
                                    val data: MatchVo = gson.fromJson(dataJson, MatchVo::class.java)
                                    Log.i("BattleMessageCallback", "$data")
                                    if (data.round == 0) {
                                        matchEnter(data)
                                    } else {
                                        match(data)
                                    }
                                }

                                PublishBattleCode.MATCH_OVER -> {
                                    val data: MatchOverVo = gson.fromJson(dataJson, MatchOverVo::class.java)
                                    Log.i("BattleMessageCallback", "$data")
                                    matchOver(data)
                                }

                                else -> {}
                            }
                        } else {}
                    }
                } catch (e: Exception) {
                    Log.e("BattleMessageCallback", "mqtt battle message parsing fail.")
                }
            }
        }
    }
    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
    override fun connectionLost(cause: Throwable?) {}
}