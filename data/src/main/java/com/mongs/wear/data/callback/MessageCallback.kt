package com.mongs.wear.data.callback

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.data.api.code.PublishCode
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
import com.mongs.wear.data.code.Shift
import com.mongs.wear.data.code.State
import com.mongs.wear.data.dataStore.MemberDataStore
import com.mongs.wear.data.dto.mqtt.res.BasicPublish
import com.mongs.wear.data.dto.mqtt.res.MemberStarPointVo
import com.mongs.wear.data.dto.mqtt.res.MongCodeVo
import com.mongs.wear.data.dto.mqtt.res.MongExpVo
import com.mongs.wear.data.dto.mqtt.res.MongIsSleepingVo
import com.mongs.wear.data.dto.mqtt.res.MongPayPointVo
import com.mongs.wear.data.dto.mqtt.res.MongPoopCountVo
import com.mongs.wear.data.dto.mqtt.res.MongShiftVo
import com.mongs.wear.data.dto.mqtt.res.MongStateVo
import com.mongs.wear.data.dto.mqtt.res.MongStatusVo
import com.mongs.wear.data.room.client.RoomDB
import java.lang.reflect.Type


class MessageCallback (
    private val memberDataStore: MemberDataStore,
    private val roomDB: RoomDB,
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

                    Log.i("MessageCallback", "[$topic] $json")

                    val body: List<BasicPublish<Any>> = gson.fromJson(json, listType)
                    body.sortedByDescending { it.createdAt }
                        .distinctBy { it.code }
                        .forEach {
                            val dataJson = gson.toJson(it.data)
                            when (it.code) {
                                PublishCode.MEMBER_STAR_POINT -> {
                                    val data: MemberStarPointVo = gson.fromJson(dataJson, MemberStarPointVo::class.java)
                                    memberDataStore.setStarPoint(starPoint = data.starPoint)
                                }

                                PublishCode.MONG_CODE -> {
                                    val data: MongCodeVo = gson.fromJson(dataJson, MongCodeVo::class.java)
                                    roomDB.slotDao().updateMongCodeByMqtt(mongId = data.mongId, mongCode = data.mongCode)
                                }

                                PublishCode.MONG_EXP -> {
                                    val data: MongExpVo = gson.fromJson(dataJson, MongExpVo::class.java)
                                    roomDB.slotDao().updateExpByMqtt(mongId = data.mongId, exp = data.expPercent)
                                }

                                PublishCode.MONG_IS_SLEEPING -> {
                                    val data: MongIsSleepingVo = gson.fromJson(dataJson, MongIsSleepingVo::class.java)
                                    roomDB.slotDao().updateIsSleepingByMqtt(mongId = data.mongId, isSleeping = data.isSleeping)
                                }

                                PublishCode.MONG_PAY_POINT -> {
                                    val data: MongPayPointVo = gson.fromJson(dataJson, MongPayPointVo::class.java)
                                    roomDB.slotDao().updatePayPointByMqtt(mongId = data.mongId, payPoint = data.payPoint)
                                }

                                PublishCode.MONG_POOP_COUNT -> {
                                    val data: MongPoopCountVo = gson.fromJson(dataJson, MongPoopCountVo::class.java)
                                    roomDB.slotDao().updatePoopCountByMqtt(mongId = data.mongId, poopCount = data.poopCount)
                                }

                                PublishCode.MONG_SHIFT -> {
                                    val data: MongShiftVo = gson.fromJson(dataJson, MongShiftVo::class.java)
                                    roomDB.slotDao().updateShiftCodeByMqtt(
                                        mongId = data.mongId,
                                        shiftCode = Shift.valueOf(data.shiftCode).code
                                    )
                                }

                                PublishCode.MONG_STATE -> {
                                    val data: MongStateVo = gson.fromJson(dataJson, MongStateVo::class.java)
                                    roomDB.slotDao().updateStateCodeByMqtt(
                                        mongId = data.mongId,
                                        stateCode = State.valueOf(data.stateCode).code
                                    )
                                }

                                PublishCode.MONG_STATUS -> {
                                    val data: MongStatusVo = gson.fromJson(dataJson, MongStatusVo::class.java)
                                    roomDB.slotDao().updateStatusByMqtt(
                                        mongId = data.mongId,
                                        weight = data.weight,
                                        strength = data.strengthPercent,
                                        satiety = data.satietyPercent,
                                        healthy = data.healthyPercent,
                                        sleep = data.sleepPercent
                                    )
                                }

                                else -> {}
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