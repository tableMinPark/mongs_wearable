package com.mongs.wear.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mongs.wear.data.client.RealTimeRepository
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
import com.mongs.wear.data.utils.GsonDateFormatAdapter
import java.lang.reflect.Type
import java.time.LocalDateTime
import javax.inject.Inject

class RealTimeRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore,
    private val roomDB: RoomDB,
): RealTimeRepository {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    // CALLBACK
    override suspend fun memberStarPointCallback(dataJson: String) {
        val data: MemberStarPointVo = gson.fromJson(dataJson, MemberStarPointVo::class.java)
        memberDataStore.setStarPoint(starPoint = data.starPoint)
    }
    override suspend fun mongCodeCallback(dataJson: String) {
        val data: MongCodeVo = gson.fromJson(dataJson, MongCodeVo::class.java)
        roomDB.slotDao().updateMongCodeByMqtt(mongId = data.mongId, mongCode = data.mongCode)
    }
    override suspend fun mongExpCallback(dataJson: String) {
        val data: MongExpVo = gson.fromJson(dataJson, MongExpVo::class.java)
        roomDB.slotDao().updateExpByMqtt(mongId = data.mongId, exp = data.expPercent)
    }
    override suspend fun mongIsSleepingCallback(dataJson: String) {
        val data: MongIsSleepingVo = gson.fromJson(dataJson, MongIsSleepingVo::class.java)
        roomDB.slotDao().updateIsSleepingByMqtt(mongId = data.mongId, isSleeping = data.isSleeping)
    }
    override suspend fun mongPayPointCallback(dataJson: String) {
        val data: MongPayPointVo = gson.fromJson(dataJson, MongPayPointVo::class.java)
        roomDB.slotDao().updatePayPointByMqtt(mongId = data.mongId, payPoint = data.payPoint)
    }
    override suspend fun mongPoopCountCallback(dataJson: String) {
        val data: MongPoopCountVo = gson.fromJson(dataJson, MongPoopCountVo::class.java)
        roomDB.slotDao().updatePoopCountByMqtt(mongId = data.mongId, poopCount = data.poopCount)
    }
    override suspend fun mongShiftCallback(dataJson: String) {
        val data: MongShiftVo = gson.fromJson(dataJson, MongShiftVo::class.java)
        roomDB.slotDao().updateShiftCodeByMqtt(
            mongId = data.mongId,
            shiftCode = Shift.valueOf(data.shiftCode).code
        )
    }
    override suspend fun mongStateCallback(dataJson: String) {
        val data: MongStateVo = gson.fromJson(dataJson, MongStateVo::class.java)
        roomDB.slotDao().updateStateCodeByMqtt(
            mongId = data.mongId,
            stateCode = State.valueOf(data.stateCode).code
        )
    }
    override suspend fun mongStatusCallback(dataJson: String) {
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
}