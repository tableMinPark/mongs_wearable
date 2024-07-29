package com.mongs.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mongs.wear.data.room.entity.MongCode
import com.mongs.wear.data.room.entity.Slot
import com.mongs.wear.domain.code.ShiftCode
import com.mongs.wear.domain.code.StateCode
import java.lang.Thread.State

@Dao
interface SlotDao {

    /**
     * SELECT 메서드
     */
    @Query("SELECT * FROM slot WHERE mongId = :mongId")
    fun selectByMongId(mongId: Long): Slot?
    @Query("SELECT * FROM slot WHERE isSelected = true")
    fun selectByIsSelectedTrue(): Slot?
    @Query("SELECT * FROM slot WHERE isSelected = true")
    fun selectByIsSelectedTrueLive(): LiveData<Slot?>
    @Query("SELECT * FROM slot")
    fun selectAll(): List<Slot>
    @Query("SELECT * FROM slot")
    fun selectAllLive(): LiveData<List<Slot>>

    /**
     * INSERT 메서드
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(slot: Slot)

    /**
     * UPDATE 메서드
     */
    @Update
    fun update(slot: Slot)
    @Query("UPDATE slot SET isSelected = :isSelected WHERE mongId = :mongId")
    fun updateIsSelectedBySetNowSlot(mongId: Long, isSelected: Boolean)
    @Query("UPDATE slot SET payPoint = :payPoint, weight = :weight, strength = :strength, satiety = :satiety, healthy = :healthy, sleep = :sleep, exp = :exp WHERE mongId = :mongId")
    fun updateByFeedMong(mongId: Long, payPoint: Int, weight: Double, strength: Double, satiety: Double, healthy: Double, sleep: Double, exp: Double)
    @Query("UPDATE slot SET isGraduateCheck = true WHERE mongId = :mongId")
    fun updateByGraduateReadyMong(mongId: Long)
    @Query("UPDATE slot SET mongCode = :mongCode, shiftCode = :shiftCode, stateCode = :stateCode, weight = :weight, strength = :strength, satiety = :satiety, healthy = :healthy, sleep = :sleep, exp = :exp WHERE mongId = :mongId")
    fun updateByEvolutionMong(mongId: Long, mongCode: String, shiftCode: ShiftCode, stateCode: StateCode, weight: Double, strength: Double, satiety: Double, healthy: Double, sleep: Double, exp: Double)
    @Query("UPDATE slot SET isSleeping = :isSleeping WHERE mongId = :mongId")
    fun updateBySleepingMong(mongId: Long, isSleeping: Boolean)
    @Query("UPDATE slot SET exp = :exp WHERE mongId = :mongId")
    fun updateByStrokeMong(mongId: Long, exp: Double)
    @Query("UPDATE slot SET payPoint = :payPoint, weight = :weight, strength = :strength, satiety = :satiety, healthy = :healthy, sleep = :sleep, exp = :exp WHERE mongId = :mongId")
    fun updateByTrainingMong(mongId: Long, weight: Double, strength: Double, satiety: Double, healthy: Double, sleep: Double, exp: Double, payPoint: Int)
    @Query("UPDATE slot SET poopCount = :poopCount, exp = :exp WHERE mongId = :mongId")
    fun updateByPoopCleanMong(mongId: Long, poopCount: Int, exp: Double)
    @Query("UPDATE slot SET isHappy = :isHappy WHERE mongId = :mongId")
    fun updateIsHappyByStrokeMong(mongId: Long, isHappy: Boolean)
    @Query("UPDATE slot SET isEating = :isEating WHERE mongId = :mongId")
    fun updateIsEatingByFeedMong(mongId: Long, isEating: Boolean)
    @Query("UPDATE slot SET isPoopCleaning = :isPoopCleaning WHERE mongId = :mongId")
    fun updateIsPoopCleaningByPoopClean(mongId: Long, isPoopCleaning: Boolean)
    @Query("UPDATE slot SET payPoint = :payPoint WHERE mongId = :mongId")
    fun updatePayPointByExchangeWalking(mongId: Long, payPoint: Int)

    @Query("UPDATE slot SET mongCode = :mongCode WHERE mongId = :mongId")
    fun updateMongCodeByMqtt(mongId: Long, mongCode: String)
    @Query("UPDATE slot SET exp = :exp WHERE mongId = :mongId")
    fun updateExpByMqtt(mongId: Long, exp: Double)
    @Query("UPDATE slot SET isSleeping = :isSleeping WHERE mongId = :mongId")
    fun updateIsSleepingByMqtt(mongId: Long, isSleeping: Boolean)
    @Query("UPDATE slot SET payPoint = :payPoint WHERE mongId = :mongId")
    fun updatePayPointByMqtt(mongId: Long, payPoint: Int)
    @Query("UPDATE slot SET poopCount = :poopCount WHERE mongId = :mongId")
    fun updatePoopCountByMqtt(mongId: Long, poopCount: Int)
    @Query("UPDATE slot SET shiftCode = :shiftCode WHERE mongId = :mongId")
    fun updateShiftCodeByMqtt(mongId: Long, shiftCode: ShiftCode)
    @Query("UPDATE slot SET stateCode = :stateCode WHERE mongId = :mongId")
    fun updateStateCodeByMqtt(mongId: Long, stateCode: StateCode)
    @Query("UPDATE slot SET weight = :weight, strength = :strength, satiety = :satiety, healthy = :healthy, sleep = :sleep WHERE mongId = :mongId")
    fun updateStatusByMqtt(mongId: Long, weight: Double, strength: Double, satiety: Double, healthy: Double, sleep: Double)

    /**
     * DELETE 메서드
     */
    @Query("DELETE FROM slot WHERE mongId = :mongId")
    fun deleteByMongId(mongId: Long)
}