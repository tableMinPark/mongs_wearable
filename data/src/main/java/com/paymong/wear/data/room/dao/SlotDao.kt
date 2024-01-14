package com.paymong.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.domain.model.SlotModel

@Dao
interface SlotDao {
    @Insert
    fun register(slot: Slot): Long
    @Query("DELETE FROM slot WHERE slotId = :slotId")
    fun deleteBySlotId(slotId: Long)
    @Query("SELECT COUNT(slotId) FROM slot")
    fun count(): Long
    @Query("SELECT slotId FROM slot ORDER BY slotId LIMIT 1")
    fun findFirstBySlotId(): Long
    @Query("SELECT * FROM slot WHERE slotId = :slotId")
    fun findBySlotId(slotId: Long): LiveData<SlotModel>
    @Query("SELECT * FROM slot")
    fun findAll(): LiveData<List<SlotModel>>
    @Query("UPDATE slot SET nextStateCode = :nextStateCode WHERE slotId = :slotId")
    fun modifyNextStateBySlotId(nextStateCode: String, slotId: Long)
    @Query("SELECT nextStateCode FROM slot WHERE slotId = :slotId")
    fun findNextStateBySlotId(slotId: Long): String
    @Query("UPDATE slot SET stateCode = :stateCode WHERE slotId = :slotId")
    fun modifyStateBySlotId(stateCode: String, slotId: Long)
    @Query("SELECT stateCode FROM slot WHERE slotId = :slotId")
    fun findStateBySlotId(slotId: Long): String
    @Query("UPDATE slot SET poopCount = :poopCount WHERE slotId = :slotId")
    fun modifyPoopCountBySlotId(poopCount: Int, slotId: Long)

    /** MQTT **/
    @Query("UPDATE slot SET health = :health, satiety = :satiety, strength = :strength, sleep = :sleep WHERE mongId = :mongId")
    fun modifyStatusByMongId(mongId: Long, health: Float, satiety: Float, strength: Float, sleep: Float)
    @Query("UPDATE slot SET stateCode = :stateCode WHERE mongId = :mongId")
    fun modifyStateByMongId(mongId: Long, stateCode: String)
    @Query("UPDATE slot SET nextMongCode = :nextMongCode WHERE mongId = :mongId")
    fun modifyNextMongCodeByMongId(mongId: Long, nextMongCode: String)
    @Query("SELECT stateCode FROM slot WHERE mongId = :mongId")
    fun findStateByMongId(mongId: Long): String
}