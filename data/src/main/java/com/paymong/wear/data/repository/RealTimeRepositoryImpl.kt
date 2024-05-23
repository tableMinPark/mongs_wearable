package com.paymong.wear.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.paymong.wear.data.client.RealTimeRepository
import com.paymong.wear.data.code.Shift
import com.paymong.wear.data.code.State
import com.paymong.wear.data.dataStore.MemberDataStore
import com.paymong.wear.data.dto.mqtt.res.BasicPublish
import com.paymong.wear.data.dto.mqtt.res.MemberStarPointVo
import com.paymong.wear.data.dto.mqtt.res.MongCodeVo
import com.paymong.wear.data.dto.mqtt.res.MongExpVo
import com.paymong.wear.data.dto.mqtt.res.MongIsSleepingVo
import com.paymong.wear.data.dto.mqtt.res.MongPayPointVo
import com.paymong.wear.data.dto.mqtt.res.MongPoopCountVo
import com.paymong.wear.data.dto.mqtt.res.MongShiftVo
import com.paymong.wear.data.dto.mqtt.res.MongStateVo
import com.paymong.wear.data.dto.mqtt.res.MongStatusVo
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.utils.GsonDateFormatAdapter
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
    override suspend fun memberStarPointCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MemberStarPointVo>>() {}.type
        val res: BasicPublish<MemberStarPointVo> = gson.fromJson(json, type)
        val body = res.data

        memberDataStore.setStarPoint(starPoint = body.starPoint)
    }

    override suspend fun mongCodeCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongCodeVo>>() {}.type
        val res: BasicPublish<MongCodeVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.mongCode = body.mongCode
            roomDB.slotDao().update(slot)
        }
    }

    override suspend fun mongExpCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongExpVo>>() {}.type
        val res: BasicPublish<MongExpVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.exp = body.expPercent
            roomDB.slotDao().update(slot)
        }
    }

    override suspend fun mongIsSleepingCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongIsSleepingVo>>() {}.type
        val res: BasicPublish<MongIsSleepingVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.isSleeping = body.isSleeping
            roomDB.slotDao().update(slot)
        }
    }

    override suspend fun mongPayPointCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongPayPointVo>>() {}.type
        val res: BasicPublish<MongPayPointVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.payPoint = body.payPoint
            roomDB.slotDao().update(slot)
        }
    }

    override suspend fun mongPoopCountCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongPoopCountVo>>() {}.type
        val res: BasicPublish<MongPoopCountVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.poopCount = body.poopCount
            roomDB.slotDao().update(slot)
        }
    }

    override suspend fun mongShiftCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongShiftVo>>() {}.type
        val res: BasicPublish<MongShiftVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.shiftCode = Shift.valueOf(body.shiftCode).code
            roomDB.slotDao().update(slot)
        }
    }

    override suspend fun mongStateCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongStateVo>>() {}.type
        val res: BasicPublish<MongStateVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.stateCode = State.valueOf(body.stateCode).code
            roomDB.slotDao().update(slot)
        }
    }

    override suspend fun mongStatusCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<MongStatusVo>>() {}.type
        val res: BasicPublish<MongStatusVo> = gson.fromJson(json, type)
        val body = res.data

        roomDB.slotDao().findByMongId(mongId = body.mongId).let { slot ->
            slot.weight = body.weight
            slot.strength = body.strengthPercent
            slot.satiety = body.satietyPercent
            slot.healthy = body.healthyPercent
            slot.sleep = body.sleepPercent
            roomDB.slotDao().update(slot)
        }
    }
}