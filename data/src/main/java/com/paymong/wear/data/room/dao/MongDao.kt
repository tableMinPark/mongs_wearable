package com.paymong.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.paymong.wear.data.room.entity.Mong
import com.paymong.wear.domain.dto.room.MongCondition
import com.paymong.wear.domain.dto.room.MongInfo
import com.paymong.wear.domain.dto.room.MongState
import java.time.LocalDateTime

@Dao
interface MongDao {
    // add slot
    @Insert
    fun registerMong(mong: Mong)
    @Update
    fun modifyMong(mong: Mong)
    // checkNull
    @Query("DELETE FROM mong WHERE slotId = :slotId")
    fun deleteMongBySlotId(slotId: Int)
    @Query("SELECT COUNT(*) FROM mong")
    fun countBySlotId(): Int
    @Query("SELECT * FROM mong WHERE slotId = :slotId")
    fun findMongBySlotId(slotId: Int): LiveData<Mong>
    @Query("SELECT health, satiety, strength, sleep FROM mong WHERE slotId = :slotId")
    fun findMongConditionBySlotId(slotId: Int): LiveData<MongCondition>
    @Query("SELECT mongName, born, weight FROM mong WHERE slotId = :slotId")
    fun findMongInfoBySlotId(slotId: Int): LiveData<MongInfo>
    @Query("SELECT stateCode, nextStateCode, poopCount, mongCode, nextMongCode FROM mong WHERE slotId = :slotId")
    fun findMongStateBySlotId(slotId: Int): LiveData<MongState>
    @Transaction
    @Query("UPDATE mong SET health = :health, satiety = :satiety, strength = :strength, sleep = :sleep WHERE slotId = :slotId")
    fun modifyMongConditionBySlotId(slotId: Int, health: Float, satiety: Float, strength: Float, sleep: Float)
    @Transaction
    @Query("UPDATE mong SET mongName = :mongName, born = :born, weight = :weight WHERE slotId = :slotId")
    fun modifyMongInfoBySlotId(slotId: Int, mongName: String, born: LocalDateTime, weight: Int)
    @Transaction
    @Query("UPDATE mong SET stateCode = :stateCode, nextStateCode = :nextStateCode, poopCount = :poopCount, mongCode = :mongCode, nextMongCode = :nextMongCode WHERE slotId = :slotId")
    fun modifyMongStateBySlotId(slotId: Int, stateCode: String, nextStateCode: String, poopCount: Int, mongCode: String, nextMongCode: String)
}