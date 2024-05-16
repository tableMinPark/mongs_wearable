package com.paymong.wear.data.repository_.notification.callback

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.paymong.wear.data.dto.mqtt.res.BasicPublish
import com.paymong.wear.data.api.notification.dto.response.PublishAttendanceVo
import com.paymong.wear.data.api.notification.dto.response.PublishCreateVo
import com.paymong.wear.data.api.notification.dto.response.PublishDeadVo
import com.paymong.wear.data.api.notification.dto.response.PublishDeleteVo
import com.paymong.wear.data.api.notification.dto.response.PublishEvolutionReadyVo
import com.paymong.wear.data.api.notification.dto.response.PublishEvolutionVo
import com.paymong.wear.data.api.notification.dto.response.PublishFeedVo
import com.paymong.wear.data.api.notification.dto.response.PublishGraduationReadyVo
import com.paymong.wear.data.api.notification.dto.response.PublishGraduationVo
import com.paymong.wear.data.api.notification.dto.response.PublishHealthyVo
import com.paymong.wear.data.api.notification.dto.response.PublishPoopVo
import com.paymong.wear.data.api.notification.dto.response.PublishSatietyVo
import com.paymong.wear.data.api.notification.dto.response.PublishSleepVo
import com.paymong.wear.data.api.notification.dto.response.PublishSleepingVo
import com.paymong.wear.data.api.notification.dto.response.PublishStateVo
import com.paymong.wear.data.api.notification.dto.response.PublishStrengthVo
import com.paymong.wear.data.api.notification.dto.response.PublishStrokeVo
import com.paymong.wear.data.api.notification.dto.response.PublishTrainingVo
import com.paymong.wear.data.api.notification.dto.response.PublishWeightVo
import com.paymong.wear.data.code.Shift
import com.paymong.wear.data.code.State
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.data.utils.GsonDateFormatAdapter
import java.lang.reflect.Type
import java.time.LocalDateTime
import javax.inject.Inject

class CallbackRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB
) : CallbackRepository {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter())
        .create()

    /**
    * 지수 감소 : 체력
    **/
    override suspend fun healthyCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishHealthyVo>>() {}.type
        val response: BasicPublish<PublishHealthyVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyHealthyByMongId(mongId = response.data.mongId, healthy = response.data.healthy)
    }
    /**
    * 지수 감소 : 포만감
    **/
    override suspend fun satietyCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishSatietyVo>>() {}.type
        val response: BasicPublish<PublishSatietyVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifySatietyByMongId(mongId = response.data.mongId, satiety = response.data.satiety)
    }
    /**
    * 지수 감소 : 피로도
    **/
    override suspend fun sleepCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishSleepVo>>() {}.type
        val response: BasicPublish<PublishSleepVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifySleepByMongId(mongId = response.data.mongId, sleep = response.data.sleep)
    }
    /**
    * 지수 감소 : 힘
    **/
    override suspend fun strengthCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishStrengthVo>>() {}.type
        val response: BasicPublish<PublishStrengthVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyStrengthByMongId(mongId = response.data.mongId, strength = response.data.strength)
    }
    /**
    * 지수 감소 : 몸무게
    **/
    override suspend fun weightCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishWeightVo>>() {}.type
        val response: BasicPublish<PublishWeightVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyWeightByMongId(mongId = response.data.mongId, weight = response.data.weight)
    }
    /**
     * 똥 증가 : 똥
     **/
    override suspend fun poopCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishPoopVo>>() {}.type
        val response: BasicPublish<PublishPoopVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyPoopCountByMongId(mongId = response.data.mongId, poopCount = response.data.poopCount)
    }



    /**
     * 몽 생성 : 슬롯 추가
     **/
    override suspend fun createCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishCreateVo>>() {}.type
        val response: BasicPublish<PublishCreateVo> = gson.fromJson(json, type)

        if (roomDB.slotDao().findByMongId(response.data.mongId) == null) {
            roomDB.slotDao().register(
                slot = Slot(
                    mongId = response.data.mongId,
                    name = response.data.name,
                    mongCode = response.data.mongCode,
                    weight = response.data.weight,
                    healthy = response.data.healthy,
                    satiety = response.data.satiety,
                    strength = response.data.strength,
                    sleep = response.data.sleep,
                    poopCount = response.data.poopCount,
                    isSleeping = response.data.isSleeping,
                    exp = response.data.exp,
                    stateCode = response.data.stateCode,
                    shiftCode = response.data.shiftCode,
                    payPoint = response.data.payPoint,
                    born = response.data.born,
                )
            )
        }
    }
    /**
     * 몽 사망 : 해당 슬롯 사망 처리 (DEAD)
     **/
    override suspend fun deadCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishDeadVo>>() {}.type
        val response: BasicPublish<PublishDeadVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyShiftCodeByMongId(
            mongId = response.data.mongId,
            shiftCode = Shift.valueOf(response.data.shiftCode).code
        )
    }
    /**
     * 몽 삭제 : 해당 슬롯 삭제 처리 (DELETE)
     **/
    override suspend fun deleteCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishDeleteVo>>() {}.type
        val response: BasicPublish<PublishDeleteVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyShiftCodeByMongId(
            mongId = response.data.mongId,
            shiftCode = Shift.valueOf(response.data.shiftCode).code
        )
    }
    /**
     * 몽 진화 준비 : 해당 슬롯 진화 대기 처리 (EVOLUTION_READY)
     **/
    override suspend fun evolutionReadyCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishEvolutionReadyVo>>() {}.type
        val response: BasicPublish<PublishEvolutionReadyVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyShiftCodeByMongId(
            mongId = response.data.mongId,
            shiftCode = Shift.valueOf(response.data.shiftCode).code
        )
    }
    /**
     * 몽 진화 : 해당 슬롯 진화 처리 (EVOLUTION)
     **/
    override suspend fun evolutionCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishEvolutionVo>>() {}.type
        val response: BasicPublish<PublishEvolutionVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyAfterEvolution(
            mongId = response.data.mongId,
            mongCode = response.data.mongCode,
            weight = response.data.weight,
            strength = response.data.strength,
            satiety = response.data.satiety,
            healthy = response.data.healthy,
            sleep = response.data.sleep,
            shiftCode = Shift.valueOf(response.data.shiftCode).code,
            stateCode = State.valueOf(response.data.stateCode).code,
            exp = response.data.exp,
        )
    }
    /**
     * 몽 졸업 준비 : 해당 슬롯 졸업 대기 처리 (GRADUATION_READY)
     **/
    override suspend fun graduationReadyCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishGraduationReadyVo>>() {}.type
        val response: BasicPublish<PublishGraduationReadyVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyShiftCodeByMongId(
            mongId = response.data.mongId,
            shiftCode = Shift.valueOf(response.data.shiftCode).code
        )
    }
    /**
     * 몽 졸업 : 해당 슬롯 졸업 처리 (GRADUATION)
     **/
    override suspend fun graduationCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishGraduationVo>>() {}.type
        val response: BasicPublish<PublishGraduationVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyShiftCodeByMongId(
            mongId = response.data.mongId,
            shiftCode = Shift.valueOf(response.data.shiftCode).code
        )
    }


    /**
     * 몽 먹이 주기 : 해당 슬롯 지수 증가
     **/
    override suspend fun feedCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishFeedVo>>() {}.type
        val response: BasicPublish<PublishFeedVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyAfterFeed(
            mongId = response.data.mongId,
            weight = response.data.weight,
            strength = response.data.strength,
            satiety = response.data.satiety,
            healthy = response.data.healthy,
            sleep = response.data.sleep,
            exp = response.data.exp,
            payPoint = response.data.payPoint
        )
    }
    /**
     * 몽 수면 : 수면 상태로 변경
     **/
    override suspend fun sleepingCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishSleepingVo>>() {}.type
        val response: BasicPublish<PublishSleepingVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyAfterSleeping(
            mongId = response.data.mongId,
            isSleeping = response.data.isSleeping
        )
    }
    /**
     * 몽 쓰다듬기 : 쓰다듬기 이후 변경 사항 적용
     **/
    override suspend fun strokeCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishStrokeVo>>() {}.type
        val response: BasicPublish<PublishStrokeVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyAfterStroke(mongId = response.data.mongId, exp = response.data.exp)
    }
    /**
     * 몽 훈련 : 훈련 이후 변경 사항 적용
     **/
    override suspend fun trainingCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishTrainingVo>>() {}.type
        val response: BasicPublish<PublishTrainingVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyAfterTraining(
            mongId = response.data.mongId,
            weight = response.data.weight,
            strength = response.data.strength,
            satiety = response.data.satiety,
            healthy = response.data.healthy,
            sleep = response.data.sleep,
            exp = response.data.exp,
            payPoint = response.data.payPoint
        )
    }
    /**
     * 로그인 보상 : 로그인 보상 exp 증가 적용
     **/
    override suspend fun attendanceCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishAttendanceVo>>() {}.type
        val response: BasicPublish<PublishAttendanceVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyExpByMongId(mongId = response.data.mongId, exp = response.data.exp)
    }
    /**
     * 몽 상태 변경 : 지정된 상태로 변경
     **/
    override suspend fun stateCallback(json: String) {
        val type: Type = object : TypeToken<BasicPublish<PublishStateVo>>() {}.type
        val response: BasicPublish<PublishStateVo> = gson.fromJson(json, type)
        roomDB.slotDao().modifyStateCodeByMongId(mongId = response.data.mongId, stateCode = State.valueOf(response.data.stateCode).code)
    }
}