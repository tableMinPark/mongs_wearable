package com.mongs.wear.data.manager.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mongs.wear.data.manager.entity.MongEntity

@Dao
interface MongDao {

    @Query("SElECT * FROM mongs_mong WHERE mongId = :mongId")
    fun findByMongId(mongId: Long) : MongEntity?

    @Query("SElECT * FROM mongs_mong WHERE mongId = :mongId")
    fun findLiveByMongId(mongId: Long) : LiveData<MongEntity?>

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    fun findByIsCurrentTrue() : MongEntity?

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    fun findAllByIsCurrentTrue() : List<MongEntity>

    @Query("SELECT * FROM mongs_mong")
    fun findAll() : List<MongEntity>

    @Query("SELECT * FROM mongs_mong")
    fun findLiveAll() : LiveData<List<MongEntity>>

    @Query("DELETE FROM mongs_mong WHERE mongId = :mongId")
    fun deleteByMongId(mongId: Long)

    @Query("DELETE FROM mongs_mong WHERE mongId NOT IN (:mongIds)")
    fun deleteByMongIdNotIn(mongIds: List<Long>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(mongEntity: MongEntity) : Long

    @Update
    fun update(mongEntity: MongEntity) : Int

    @Transaction
    fun save(mongEntity: MongEntity) : MongEntity {

        val isSuccess = this.insert(mongEntity = mongEntity)

        if (isSuccess == -1L) this.update(mongEntity = mongEntity)

        return mongEntity
    }
}