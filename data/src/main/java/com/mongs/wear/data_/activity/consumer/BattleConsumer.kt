package com.mongs.wear.data_.activity.consumer

import android.util.Log
import com.google.gson.Gson
import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data_.activity.dto.response.CreateBattleResponseDto
import com.mongs.wear.data_.activity.dto.response.FightBattleResponseDto
import com.mongs.wear.data_.activity.dto.response.OverBattleResponseDto
import com.mongs.wear.domain.repositroy.BattleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class BattleConsumer (
    private val battleRepository: BattleRepository,
    private val gson: Gson,
) : MqttCallback {

    companion object {

        private const val ACTIVITY_BATTLE_FIND_MATCHING = "ACTIVITY-BATTLE-002"

        private const val ACTIVITY_BATTLE_ENTER_ALL_BATTLE_PLAYER = "ACTIVITY-BATTLE-003"

        private const val ACTIVITY_BATTLE_OVER_BATTLE = "ACTIVITY-BATTLE-004"

        private const val ACTIVITY_BATTLE_FIGHT_BATTLE = "ACTIVITY-BATTLE-005"
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {

        CoroutineScope(Dispatchers.IO).launch {
            message?.let {
                topic?.let {

                    try {

                        val responseDto = gson.fromJson(message.toString(), ResponseDto::class.java)

                        val resultJson = gson.toJson(responseDto.result)

                        when (responseDto.code) {

                            ACTIVITY_BATTLE_FIND_MATCHING -> {
                                val result =
                                    gson.fromJson(resultJson, CreateBattleResponseDto::class.java)

                            }

                            ACTIVITY_BATTLE_ENTER_ALL_BATTLE_PLAYER -> {
                                val result =
                                    gson.fromJson(resultJson, FightBattleResponseDto::class.java)

                            }

                            ACTIVITY_BATTLE_OVER_BATTLE -> {
                                val result =
                                    gson.fromJson(resultJson, OverBattleResponseDto::class.java)

                            }

                            ACTIVITY_BATTLE_FIGHT_BATTLE -> {
                                val result =
                                    gson.fromJson(resultJson, FightBattleResponseDto::class.java)

                            }
                        }
                    } catch (e: Exception) {
                        Log.e("BattleConsumer", "battle mqtt message parsing fail.")
                    }
                }
            }
        }
    }

    override fun connectionLost(cause: Throwable?) {}

    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
}