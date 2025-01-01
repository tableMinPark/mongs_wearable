package com.mongs.wear.data.common.consumer

import android.util.Log
import com.google.gson.Gson
import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data.activity.dto.response.CreateBattleResponseDto
import com.mongs.wear.data.activity.dto.response.FightBattleResponseDto
import com.mongs.wear.data.activity.dto.response.OverBattleResponseDto
import com.mongs.wear.data.common.client.MqttClientImpl
import com.mongs.wear.data.common.datastore.AppDataStore
import com.mongs.wear.data.manager.dto.response.MongObserveResponseDto
import com.mongs.wear.data.manager.dto.response.MongStateObserveResponseDto
import com.mongs.wear.data.manager.dto.response.MongStatusObserveResponseDto
import com.mongs.wear.data.user.dto.response.PlayerObserveResponseDto
import com.mongs.wear.data.common.resolver.ObserveResolver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class MqttConsumer (
    private val observeResolver: ObserveResolver,
    private val appDataStore: AppDataStore,
    private val gson: Gson,
) : MqttCallback {

    companion object {

        private const val ACTIVITY_BATTLE_FIND_MATCHING = "ACTIVITY-BATTLE-002"
        private const val ACTIVITY_BATTLE_ENTER_ALL_BATTLE_PLAYER = "ACTIVITY-BATTLE-003"
        private const val ACTIVITY_BATTLE_OVER_BATTLE = "ACTIVITY-BATTLE-004"
        private const val ACTIVITY_BATTLE_FIGHT_BATTLE = "ACTIVITY-BATTLE-005"
        private const val MANAGER_MANAGEMENT_OBSERVE_MONG = "MANAGER-MANAGEMENT-011"
        private const val MANAGER_MANAGEMENT_OBSERVE_MONG_STATE = "MANAGER-MANAGEMENT-012"
        private const val MANAGER_MANAGEMENT_OBSERVE_MONG_STATUS = "MANAGER-MANAGEMENT-013"
        private const val USER_PLAYER_OBSERVE_MEMBER = "USER-PLAYER-000"
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {

        CoroutineScope(Dispatchers.IO).launch {
            message?.let {
                topic?.let {
                    try {
                        val responseDto = gson.fromJson(message.toString(), ResponseDto::class.java)

                        val resultJson = gson.toJson(responseDto.result)

                        Log.i("MqttConsumer", "$responseDto")

                        when (responseDto.code) {

                            ACTIVITY_BATTLE_FIND_MATCHING ->
                                observeResolver.updateSearchMatch(createBattleResponseDto = gson.fromJson(resultJson, CreateBattleResponseDto::class.java))

                            ACTIVITY_BATTLE_ENTER_ALL_BATTLE_PLAYER ->
                                observeResolver.updateBattleMatchEnter(fightBattleResponseDto = gson.fromJson(resultJson, FightBattleResponseDto::class.java))

                            ACTIVITY_BATTLE_OVER_BATTLE ->
                                observeResolver.updateBattleMatchOver(overBattleResponseDto = gson.fromJson(resultJson, OverBattleResponseDto::class.java))

                            ACTIVITY_BATTLE_FIGHT_BATTLE ->
                                observeResolver.updateBattleMatchFight(fightBattleResponseDto = gson.fromJson(resultJson, FightBattleResponseDto::class.java))

                            MANAGER_MANAGEMENT_OBSERVE_MONG ->
                                observeResolver.updateMong(mongObserveResponseDto = gson.fromJson(resultJson, MongObserveResponseDto::class.java))

                            MANAGER_MANAGEMENT_OBSERVE_MONG_STATE ->
                                observeResolver.updateMongState(mongStateObserveResponseDto = gson.fromJson(resultJson, MongStateObserveResponseDto::class.java))

                            MANAGER_MANAGEMENT_OBSERVE_MONG_STATUS ->
                                observeResolver.updateMongStatus(mongStatusObserveResponseDto = gson.fromJson(resultJson, MongStatusObserveResponseDto::class.java))

                            USER_PLAYER_OBSERVE_MEMBER ->
                                observeResolver.updatePlayer(playerObserveResponseDto = gson.fromJson(resultJson, PlayerObserveResponseDto::class.java))
                        }
                    } catch (e: Exception) {
                        Log.e("BattleConsumer", "battle mqtt message parsing fail.")
                    }
                }
            }
        }
    }

    override fun connectionLost(cause: Throwable?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (MqttClientImpl.mqttState.broker != MqttClientImpl.MqttState.MqttStateCode.PAUSE_DISCONNECT) {
                MqttClientImpl.mqttState.broker = MqttClientImpl.MqttState.MqttStateCode.DISCONNECT
                appDataStore.setNetwork(network = false)
            }
        }
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
}