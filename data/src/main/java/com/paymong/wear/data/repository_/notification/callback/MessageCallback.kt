package com.paymong.wear.data.repository_.notification.callback

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paymong.wear.data.api.code.PublishCode
import com.paymong.wear.data.dto.mqtt.res.BasicPublish
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
    private val callbackRepository: CallbackRepository
) : MqttCallback {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        message?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val json = it.toString()

                    Log.d("MessageCallback", "json: $json")

                    val response = gson.fromJson(json, BasicPublish::class.java)

                    when (response.code) {
                        PublishCode.MONG_CREATE -> {
                            callbackRepository.createCallback(json)
                        }

                        PublishCode.MONG_DELETE -> {
                            callbackRepository.deleteCallback(json)
                        }

                        PublishCode.MONG_DEAD -> {
                            callbackRepository.deadCallback(json)
                        }

                        PublishCode.MONG_STROKE -> {
                            callbackRepository.strokeCallback(json)
                        }

                        PublishCode.MONG_FEED -> {
                            callbackRepository.feedCallback(json)
                        }

                        PublishCode.MONG_SLEEPING -> {
                            callbackRepository.sleepingCallback(json)
                        }

                        PublishCode.MONG_POOP -> {
                            callbackRepository.poopCallback(json)
                        }

                        PublishCode.MONG_TRAINING -> {
                            callbackRepository.trainingCallback(json)
                        }

                        PublishCode.MONG_GRADUATION -> {
                            callbackRepository.graduationCallback(json)
                        }

                        PublishCode.MONG_GRADUATION_READY -> {
                            callbackRepository.graduationReadyCallback(json)
                        }


                        PublishCode.MONG_EVOLUTION -> {
                            callbackRepository.evolutionCallback(json)
                        }

                        PublishCode.MONG_EVOLUTION_READY -> {
                            callbackRepository.evolutionReadyCallback(json)
                        }

                        PublishCode.MONG_SLEEP -> {
                            callbackRepository.sleepCallback(json)
                        }

                        PublishCode.MONG_HEALTHY -> {
                            callbackRepository.healthyCallback(json)
                        }

                        PublishCode.MONG_SATIETY -> {
                            callbackRepository.satietyCallback(json)
                        }

                        PublishCode.MONG_STRENGTH -> {
                            callbackRepository.strengthCallback(json)
                        }

                        PublishCode.MONG_WEIGHT -> {
                            callbackRepository.weightCallback(json)
                        }

                        PublishCode.MONG_STATE -> {
                            callbackRepository.stateCallback(json)
                        }

                        PublishCode.MONG_ATTENDANCE -> {
                            callbackRepository.attendanceCallback(json)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
    override fun connectionLost(cause: Throwable?) {
        Log.d("MqttRepositoryImpl", "[MQTT CONNECT DISABLE]")
    }
}