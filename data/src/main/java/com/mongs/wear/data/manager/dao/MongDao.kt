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
    suspend fun findByMongId(mongId: Long) : MongEntity?

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    suspend fun findByIsCurrentTrue() : MongEntity?

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    suspend fun findLiveByIsCurrentTrue() : LiveData<MongEntity?>

    @Query("SELECT * FROM mongs_mong WHERE isCurrent = true")
    suspend fun findAllByIsCurrentTrue() : List<MongEntity>

    @Query("SELECT * FROM mongs_mong")
    suspend fun findLiveAll() : LiveData<List<MongEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mongEntity: MongEntity) : Long

    @Update
    suspend fun update(mongEntity: MongEntity) : Int

    @Transaction
    suspend fun save(mongEntity: MongEntity) : MongEntity {

        val isSuccess = this.insert(mongEntity = mongEntity)

        if (isSuccess == -1L) this.update(mongEntity = mongEntity)

        return mongEntity
    }
}