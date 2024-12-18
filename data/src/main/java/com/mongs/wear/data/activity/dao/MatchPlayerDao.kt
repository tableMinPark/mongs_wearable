package com.mongs.wear.data.activity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mongs.wear.data.activity.entity.MatchPlayerEntity

@Dao
interface MatchPlayerDao {

    @Query("SELECT * FROM mongs_match_player WHERE playerId = :playerId")
    suspend fun findByPlayerId(playerId: String) : MatchPlayerEntity?

    @Query("SELECT playerId FROM mongs_match_player WHERE isMe = true")
    suspend fun findPlayerIdByIsMeTrue() : String?

    @Query("SELECT * FROM mongs_match_player WHERE isMe = true")
    suspend fun findLiveByPlayerIdIsMeTrue() : LiveData<MatchPlayerEntity?>

    @Query("SELECT * FROM mongs_match_player WHERE isMe = false")
    suspend fun findLiveByPlayerIdIsMeFalse() : LiveData<MatchPlayerEntity?>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(matchPlayerEntity: MatchPlayerEntity) : Long

    @Update
    suspend fun update(matchPlayerEntity: MatchPlayerEntity) : Int

    @Transaction
    suspend fun save(matchPlayerEntity: MatchPlayerEntity) : MatchPlayerEntity {

        val isSuccess = this.insert(matchPlayerEntity = matchPlayerEntity)

        if (isSuccess == -1L) this.update(matchPlayerEntity = matchPlayerEntity)

        return matchPlayerEntity
    }
}