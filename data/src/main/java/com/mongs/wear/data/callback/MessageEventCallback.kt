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
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.code.StateCode
import java.lang.reflect.Type


class MessageEventCallback (
    private val setStarPoint: suspend (Int) -> Unit,
    private val updateMongCode: suspend (Long, String) -> Unit,
    private val updateExp: suspend (Long, Double) -> Unit,
    private val updateIsSleeping: suspend (Long, Boolean) -> Unit,
    private val updatePayPoint: suspend (Long, Int) -> Unit,
    private val updatePoopCount: suspend (Long, Int) -> Unit,
    private val updateShiftCode: suspend (Long, String) -> Unit,
    private val updateStateCode: suspend (Long, String) -> Unit,
    private val updateStatus: suspend (Long, Double, Double, Double, Double, Double) -> Unit,
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
                                    setStarPoint(data.starPoint)
                                }

                                PublishEventCode.MONG_CODE -> {
                                    val data: MongCodeVo = gson.fromJson(dataJson, MongCodeVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updateMongCode(data.mongId, data.mongCode)
                                }

                                PublishEventCode.MONG_EXP -> {
                                    val data: MongExpVo = gson.fromJson(dataJson, MongExpVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updateExp(data.mongId, data.expPercent)
                                }

                                PublishEventCode.MONG_IS_SLEEPING -> {
                                    val data: MongIsSleepingVo = gson.fromJson(dataJson, MongIsSleepingVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updateIsSleeping(data.mongId, data.isSleeping)
                                }

                                PublishEventCode.MONG_PAY_POINT -> {
                                    val data: MongPayPointVo = gson.fromJson(dataJson, MongPayPointVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updatePayPoint(data.mongId, data.payPoint)
                                }

                                PublishEventCode.MONG_POOP_COUNT -> {
                                    val data: MongPoopCountVo = gson.fromJson(dataJson, MongPoopCountVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updatePoopCount(data.mongId, data.poopCount)
                                }

                                PublishEventCode.MONG_SHIFT -> {
                                    val data: MongShiftVo = gson.fromJson(dataJson, MongShiftVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updateShiftCode(data.mongId, data.shiftCode)
                                }

                                PublishEventCode.MONG_STATE -> {
                                    val data: MongStateVo = gson.fromJson(dataJson, MongStateVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updateStateCode(data.mongId, data.stateCode)
                                }

                                PublishEventCode.MONG_STATUS -> {
                                    val data: MongStatusVo = gson.fromJson(dataJson, MongStatusVo::class.java)
                                    Log.i("EventMessageCallback", "$data")
                                    updateStatus(data.mongId, data.weight, data.strengthPercent, data.satietyPercent, data.healthyPercent, data.sleepPercent)
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