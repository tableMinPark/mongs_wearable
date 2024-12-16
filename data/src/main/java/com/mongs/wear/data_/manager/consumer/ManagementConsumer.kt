package com.mongs.wear.data_.manager.consumer

import android.util.Log
import com.google.gson.Gson
import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data_.manager.dto.response.MongObserveResponseDto
import com.mongs.wear.data_.manager.dto.response.MongStateObserveResponseDto
import com.mongs.wear.data_.manager.dto.response.MongStatusObserveResponseDto
import com.mongs.wear.domain.repositroy.ManagementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class ManagementConsumer(
    private val managementRepository: ManagementRepository,
    private val gson: Gson,
) : MqttCallback {

    companion object {

        private const val MANAGER_MANAGEMENT_OBSERVE_MONG = "MANAGER-MANAGEMENT-010"

        private const val MANAGER_MANAGEMENT_OBSERVE_MONG_STATE = "MANAGER-MANAGEMENT-011"

        private const val MANAGER_MANAGEMENT_OBSERVE_MONG_STATUS = "MANAGER-MANAGEMENT-012"
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        CoroutineScope(Dispatchers.IO).launch {
            message?.let {
                topic?.let {

                    try {

                        val responseDto = gson.fromJson(message.toString(), ResponseDto::class.java)

                        val resultJson = gson.toJson(responseDto.result)

                        when (responseDto.code) {

                            MANAGER_MANAGEMENT_OBSERVE_MONG -> {
                                val result =
                                    gson.fromJson(resultJson, MongObserveResponseDto::class.java)

                            }

                            MANAGER_MANAGEMENT_OBSERVE_MONG_STATE -> {
                                val result =
                                    gson.fromJson(resultJson, MongStateObserveResponseDto::class.java)

                            }

                            MANAGER_MANAGEMENT_OBSERVE_MONG_STATUS -> {
                                val result =
                                    gson.fromJson(resultJson, MongStatusObserveResponseDto::class.java)

                            }
                        }

                    } catch (e: Exception) {
                        Log.e("ManagementConsumer", "management mqtt message parsing fail.")
                    }
                }
            }
        }
    }

    override fun connectionLost(cause: Throwable?) {}

    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
}