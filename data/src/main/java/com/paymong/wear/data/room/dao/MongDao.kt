package com.paymong.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paymong.wear.data.entity.Mong
import com.paymong.wear.domain.model.MongModel

@Dao
interface MongDao {
    @Insert
    fun registerMong(mong: Mong): Long
    @Query("SELECT COUNT(slotId) FROM mong")
    fun countMong(): Long
    @Query("SELECT slotId FROM mong ORDER BY slotId LIMIT 1")
    fun findFirstMongSlotId(): Long
    @Query("SELECT * FROM mong WHERE slotId = :slotId")
    fun findBySlotId(slotId: Long): LiveData<MongModel>
    @Query("SELECT * FROM mong")
    fun findAllMong(): LiveData<List<MongModel>>
    @Query("UPDATE mong SET nextStateCode = :nextStateCode WHERE slotId = :slotId")
    fun setMongNextState(nextStateCode: String, slotId: Long)
    @Query("SELECT nextStateCode FROM mong WHERE slotId = :slotId")
    fun getMongNextState(slotId: Long): String
    @Query("UPDATE mong SET stateCode = :stateCode WHERE slotId = :slotId")
    fun setMongState(stateCode: String, slotId: Long)
    @Query("SELECT stateCode FROM mong WHERE slotId = :slotId")
    fun getMongState(slotId: Long): String
    @Query("UPDATE mong SET poopCount = :poopCount WHERE slotId = :slotId")
    fun setPoopCount(poopCount: Int, slotId: Long)
//    @Update
//    fun modifyMong(mong: Mong)
//    // checkNull
//    @Query("DELETE FROM mong WHERE slotId = :slotId")
//    fun deleteMongBySlotId(slotId: Int)
//    @Query("SELECT COUNT(*) FROM mong")
//    fun countBySlotId(): Int
//    @Query("SELECT * FROM mong WHERE slotId = :slotId")
//    fun findMongBySlotId(slotId: Int): LiveData<Mong>
//    @Query("SELECT health, satiety, strength, sleep FROM mong WHERE slotId = :slotId")
//    fun findMongConditionBySlotId(slotId: Int): LiveData<MongCondition>
//    @Query("SELECT mongName, born, weight FROM mong WHERE slotId = :slotId")
//    fun findMongInfoBySlotId(slotId: Int): LiveData<MongInfo>
//    @Query("SELECT stateCode, nextStateCode, poopCount, mongCode, nextMongCode FROM mong WHERE slotId = :slotId")
//    fun findMongStateBySlotId(slotId: Int): LiveData<MongState>
//    @Transaction
//    @Query("UPDATE mong SET health = :health, satiety = :satiety, strength = :strength, sleep = :sleep WHERE slotId = :slotId")
//    fun modifyMongConditionBySlotId(slotId: Int, health: Float, satiety: Float, strength: Float, sleep: Float)
//    @Transaction
//    @Query("UPDATE mong SET mongName = :mongName, born = :born, weight = :weight WHERE slotId = :slotId")
//    fun modifyMongInfoBySlotId(slotId: Int, mongName: String, born: LocalDateTime, weight: Int)
//    @Transaction
//    @Query("UPDATE mong SET stateCode = :stateCode, nextStateCode = :nextStateCode, poopCount = :poopCount, mongCode = :mongCode, nextMongCode = :nextMongCode WHERE slotId = :slotId")
//    fun modifyMongStateBySlotId(slotId: Int, stateCode: String, nextStateCode: String, poopCount: Int, mongCode: String, nextMongCode: String)
}