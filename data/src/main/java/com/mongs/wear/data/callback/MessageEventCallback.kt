package com.mongs.wear.data.callback

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mongs.wear.data.api.code.PublishEventCode
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
import com.mongs.wear.data.dto.mqttEvent.BasicEventPublish
import com.mongs.wear.data.dto.mqttEvent.res.MemberStarPointVo
import com.mongs.wear.data.dto.mqttEvent.res.MongCodeVo
import com.mongs.wear.data.dto.mqttEvent.res.MongExpVo
import com.mongs.wear.data.dto.mqttEvent.res.MongIsSleepingVo
import com.mongs.wear.data.dto.mqttEvent.res.MongPayPointVo
import com.mongs.wear.data.dto.mqttEvent.res.MongPoopCountVo
import com.mongs.wear.data.dto.mqttEvent.res.MongShiftVo
import com.mongs.wear.data.dto.mqttEvent.res.MongStateVo
import com.mongs.wear.data.dto.mqttEvent.res.MongStatusVo
import com.mongs.wear.data.room.client.RoomDB
import java.lang.reflect.Type


class MessageEventCallback (
    private val memberDataStore: MemberDataStore,
    private val roomDB: RoomDB,
) : MqttCallback {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    private val listType: Type = object : TypeToken<List<BasicEventPublish<Any>>>() {}.type

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        message?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val json = it.toString()
                    val body: List<BasicEventPublish<Any>> = gson.fromJson(json, listType)
                    body.sortedByDescending { it.createdAt }
                        .distinctBy { it.code }
                        .forEach {
                            val dataJson = gson.toJson(it.data)
                            when (it.code) {
                                PublishEventCode.MEMBER_STAR_POINT -> {
                                    val data: MemberStarPointVo = gson.fromJson(dataJson, MemberStarPointVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    memberDataStore.setStarPoint(starPoint = data.starPoint)
                                }

                                PublishEventCode.MONG_CODE -> {
                                    val data: MongCodeVo = gson.fromJson(dataJson, MongCodeVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    roomDB.slotDao().updateMongCodeByMqtt(mongId = data.mongId, mongCode = data.mongCode)
                                }

                                PublishEventCode.MONG_EXP -> {
                                    val data: MongExpVo = gson.fromJson(dataJson, MongExpVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    roomDB.slotDao().updateExpByMqtt(mongId = data.mongId, exp = data.expPercent)
                                }

                                PublishEventCode.MONG_IS_SLEEPING -> {
                                    val data: MongIsSleepingVo = gson.fromJson(dataJson, MongIsSleepingVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    roomDB.slotDao().updateIsSleepingByMqtt(mongId = data.mongId, isSleeping = data.isSleeping)
                                }

                                PublishEventCode.MONG_PAY_POINT -> {
                                    val data: MongPayPointVo = gson.fromJson(dataJson, MongPayPointVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    roomDB.slotDao().updatePayPointByMqtt(mongId = data.mongId, payPoint = data.payPoint)
                                }

                                PublishEventCode.MONG_POOP_COUNT -> {
                                    val data: MongPoopCountVo = gson.fromJson(dataJson, MongPoopCountVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    roomDB.slotDao().updatePoopCountByMqtt(mongId = data.mongId, poopCount = data.poopCount)
                                }

                                PublishEventCode.MONG_SHIFT -> {
                                    val data: MongShiftVo = gson.fromJson(dataJson, MongShiftVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    roomDB.slotDao().updateShiftCodeByMqtt(
                                        mongId = data.mongId,
                                        shiftCode = Shift.valueOf(data.shiftCode).code
                                    )
                                }

                                PublishEventCode.MONG_STATE -> {
                                    val data: MongStateVo = gson.fromJson(dataJson, MongStateVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    roomDB.slotDao().updateStateCodeByMqtt(
                                        mongId = data.mongId,
                                        stateCode = State.valueOf(data.stateCode).code
                                    )
                                }

                                PublishEventCode.MONG_STATUS -> {
                                    val data: MongStatusVo = gson.fromJson(dataJson, MongStatusVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
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