package com.mongs.wear.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mongs.wear.data.code.BattleState
import com.mongs.wear.data.code.MatchState
import com.mongs.wear.data.room.entity.Match
import com.mongs.wear.data.room.entity.MatchPlayer

@Dao
interface MatchPlayerDao {
    @Insert
    fun insert(matchPlayer: MatchPlayer)
    @Query("DELETE FROM `match_player`")
    fun deleteAll()
    @Query("SELECT * FROM `match_player` WHERE isMe = true")
    fun selectMyMatchPlayer(): MatchPlayer?
    @Query("SELECT * FROM `match_player` WHERE isMe = true")
    fun selectMyMatchPlayerLive(): LiveData<MatchPlayer>
    @Query("SELECT * FROM `match_player` WHERE isMe = false")
    fun selectOtherMatchPlayer(): MatchPlayer?
    @Query("SELECT * FROM `match_player` WHERE isMe = false")
    fun selectOtherMatchPlayerLive(): LiveData<MatchPlayer>
    @Query("UPDATE match_player SET hp = :hp, state = :state WHERE playerId = :playerId")
    fun updateMatchPlayer(playerId: String, hp: Double, state: BattleState)
    @Query("UPDATE match_player SET isWinner = true WHERE playerId = :playerId")
    fun updateMatchWinnerPlayer(playerId: String)
}