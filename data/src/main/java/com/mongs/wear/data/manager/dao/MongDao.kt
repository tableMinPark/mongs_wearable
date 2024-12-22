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

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    fun findByIsCurrentTrue() : MongEntity?

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    fun findLiveByIsCurrentTrue() : LiveData<MongEntity?>

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    fun findAllByIsCurrentTrue() : List<MongEntity>

    @Query("SELECT * FROM mongs_mong")
    fun findLiveAll() : LiveData<List<MongEntity>>


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