package com.mongs.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mongs.wear.data.code.MatchState
import com.mongs.wear.data.room.entity.Match

@Dao
interface MatchDao {
    @Insert
    fun insert(match: Match)
    @Query("DELETE FROM `match`")
    fun deleteAll()
    @Query("SELECT * FROM `match` ORDER BY createdAt DESC LIMIT 1")
    fun select(): Match?
    @Query("SELECT * FROM `match` ORDER BY createdAt DESC LIMIT 1")
    fun selectLive(): LiveData<Match>
    @Query("UPDATE `match` SET matchState = :matchState WHERE roomId = :roomId")
    fun updateMatchState(matchState: MatchState, roomId: String)
    @Query("UPDATE `match` SET isMatchOver = :isMatchOver WHERE roomId = :roomId")
    fun updateIsMatchOver(isMatchOver: Boolean, roomId: String)
    @Query("UPDATE `match` SET round = :round WHERE roomId = :roomId")
    fun updateMatch(round: Int, roomId: String)
}