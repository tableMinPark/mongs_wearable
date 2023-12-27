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
}