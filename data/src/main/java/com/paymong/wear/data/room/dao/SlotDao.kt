package com.paymong.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.code.StateCode

@Dao
interface SlotDao {
    /**
     *
     * id 값 조회
     *
     */
    @Query("SELECT mongId FROM slot WHERE isSelected = 1")
    fun findMongIdIsSelectedTrue(): Long?

    /**
     *
     * 슬롯 관리 (생성, 삭제, 전체 조회 등)
     *
     */
    @Update
    fun modifySlot(slot: Slot)
    @Insert
    fun register(slot: Slot)
    @Query("DELETE FROM slot WHERE mongId NOT IN ( :mongIdList )")
    fun deleteByMongIdIn(mongIdList: List<Long>)
    @Query("SELECT * FROM slot WHERE mongId = :mongId")
    fun findByMongId(mongId: Long): Slot?
    @Query("SELECT mongId FROM slot ORDER BY mongId DESC LIMIT 1")
    fun findTopMongIdOrderByMongId(): Long?
    @Query("SELECT COUNT(mongId) FROM slot WHERE isSelected = 1")
    fun countIsSelectedTrue(): Int
    @Query("UPDATE slot SET isSelected = :isSelected WHERE mongId = :mongId")
    fun modifyIsSelectedByMongId(mongId: Long, isSelected: Boolean)
    @Query("SELECT * FROM slot WHERE isSelected = 1")
    fun findByIsSelectedTrue(): LiveData<Slot>
    @Query("SELECT * FROM slot")
    fun findAll(): LiveData<List<Slot>>
    @Query("DELETE FROM slot WHERE mongId = :mongId")
    fun deleteByMongId(mongId: Long)
    @Query("DELETE FROM slot WHERE shiftCode = :shiftCode")
    fun deleteByIsDeletedTrue(shiftCode: ShiftCode = ShiftCode.DELETE)

    /**
     *
     * 상태 변경 사항 적용
     *
     */
    @Query("UPDATE slot SET stateCode = :stateCode WHERE isSelected = 1")
    fun modifyStateCodeIsSelectedTrue(stateCode: StateCode)
    @Query("SELECT stateCode FROM slot WHERE isSelected = 1")
    fun findStateCodeIsSelectedTrue(): String
    @Query("UPDATE slot SET shiftCode = :shiftCode WHERE isSelected = 1")
    fun modifyShiftCodeIsSelectedTrue(shiftCode: ShiftCode)
    @Query("SELECT shiftCode FROM slot WHERE isSelected = 1")
    fun findShiftCodeIsSelectedTrue(): String
    @Query("UPDATE slot SET isHappy = :isHappy WHERE mongId = :mongId")
    fun modifyIsHappyByMongId(isHappy: Boolean, mongId: Long)
    @Query("UPDATE slot SET isEating = :isEating WHERE mongId = :mongId")
    fun modifyIsEatingByMongId(isEating: Boolean, mongId: Long)

    /**
     *
     * 지수 변경 사항 적용
     *
     */
    @Query("UPDATE slot SET weight = :weight WHERE mongId = :mongId")
    fun modifyWeightByMongId(mongId: Long, weight: Double)
    @Query("UPDATE slot SET strength = :strength WHERE mongId = :mongId")
    fun modifyStrengthByMongId(mongId: Long, strength: Double)
    @Query("UPDATE slot SET satiety = :satiety WHERE mongId = :mongId")
    fun modifySatietyByMongId(mongId: Long, satiety: Double)
    @Query("UPDATE slot SET healthy = :healthy WHERE mongId = :mongId")
    fun modifyHealthyByMongId(mongId: Long, healthy: Double)
    @Query("UPDATE slot SET sleep = :sleep WHERE mongId = :mongId")
    fun modifySleepByMongId(mongId: Long, sleep: Double)
    @Query("UPDATE slot SET exp = :exp WHERE mongId = :mongId")
    fun modifyExpByMongId(mongId: Long, exp: Double)
    @Query("UPDATE slot SET poopCount = :poopCount WHERE mongId = :mongId")
    fun modifyPoopCountByMongId(mongId: Long, poopCount: Int)

    /**
     *
     * 스케줄링 이후 변경 사항 적용
     *
     */
    @Query("UPDATE slot SET shiftCode = :shiftCode WHERE mongId = :mongId")
    fun modifyShiftCodeByMongId(mongId: Long, shiftCode: ShiftCode)
    @Query("UPDATE slot SET stateCode = :stateCode WHERE mongId = :mongId")
    fun modifyStateCodeByMongId(mongId: Long, stateCode: StateCode)
    @Query("UPDATE slot SET isGraduateCheck = true WHERE mongId = :mongId")
    fun modifyIsGraduateCheckTrueByMongId(mongId: Long)


    /**
     *
     * 활동 이후 변경 사항 적용
     *
     */
    @Query("UPDATE slot SET exp = :exp WHERE mongId = :mongId")
    fun modifyAfterStroke(mongId: Long, exp: Double)
    @Query("UPDATE slot SET isSleeping = :isSleeping WHERE mongId = :mongId")
    fun modifyAfterSleeping(mongId: Long, isSleeping: Boolean)
    @Query("UPDATE slot SET poopCount = :poopCount, exp = :exp WHERE mongId = :mongId")
    fun modifyAfterPoopClean(mongId: Long, poopCount: Int, exp: Double)
    @Query("UPDATE slot SET " +
            "weight = :weight, " +
            "strength = :strength, " +
            "satiety = :satiety, " +
            "healthy = :healthy, " +
            "sleep = :sleep, " +
            "exp = :exp, " +
            "payPoint = :payPoint " +
            "WHERE mongId = :mongId")
    fun modifyAfterFeed(
        mongId: Long,
        weight: Double,
        strength: Double,
        satiety: Double,
        healthy: Double,
        sleep: Double,
        exp: Double,
        payPoint: Int)
    @Query("UPDATE slot SET " +
            "weight = :weight, " +
            "strength = :strength, " +
            "satiety = :satiety, " +
            "healthy = :healthy, " +
            "sleep = :sleep, " +
            "exp = :exp, " +
            "stateCode = :stateCode, " +
            "shiftCode = :shiftCode, " +
            "mongCode = :mongCode " +
            "WHERE mongId = :mongId")
    fun modifyAfterEvolution(
        mongId: Long,
        mongCode: String,
        weight: Double,
        strength: Double,
        satiety: Double,
        healthy: Double,
        sleep: Double,
        shiftCode: ShiftCode,
        stateCode: StateCode,
        exp: Double)
    @Query("UPDATE slot SET " +
            "weight = :weight, " +
            "strength = :strength, " +
            "satiety = :satiety, " +
            "healthy = :healthy, " +
            "sleep = :sleep, " +
            "exp = :exp, " +
            "payPoint = :payPoint " +
            "WHERE mongId = :mongId")
    fun modifyAfterTraining(
        mongId: Long,
        weight: Double,
        strength: Double,
        satiety: Double,
        healthy: Double,
        sleep: Double,
        exp: Double,
        payPoint: Int)
}