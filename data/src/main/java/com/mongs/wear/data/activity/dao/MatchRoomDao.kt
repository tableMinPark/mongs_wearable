package com.mongs.wear.data.activity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mongs.wear.data.activity.entity.MatchRoomEntity

@Dao
interface MatchRoomDao {

    @Query("SELECT * FROM mongs_match_room WHERE deviceId = :deviceId")
    suspend fun findByDeviceId(deviceId: String) : MatchRoomEntity?

    @Query("SELECT * FROM mongs_match_room WHERE deviceId = :deviceId")
    suspend fun findLiveByDeviceId(deviceId: String) : LiveData<MatchRoomEntity?>

    @Query("SELECT * FROM mongs_match_room WHERE roomId = :roomId")
    suspend fun findByRoomId(roomId: Long) : MatchRoomEntity?


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(matchRoomEntity: MatchRoomEntity) : Long

    @Update
    suspend fun update(matchRoomEntity: MatchRoomEntity) : Int

    @Transaction
    suspend fun save(matchRoomEntity: MatchRoomEntity) : MatchRoomEntity {

        val isSuccess = this.insert(matchRoomEntity = matchRoomEntity)

        if (isSuccess == -1L) this.update(matchRoomEntity = matchRoomEntity)

        return matchRoomEntity
    }
}