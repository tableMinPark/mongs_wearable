package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.data.api.client.BattleApi
import com.mongs.wear.data.code.BattleState
import com.mongs.wear.data.code.MatchState
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.vo.MatchPlayerVo
import com.mongs.wear.domain.vo.MatchVo
import javax.inject.Inject

class BattleRepositoryImpl @Inject constructor(
    private val battleApi: BattleApi,
    private val roomDB: RoomDB,
): BattleRepository {
    override suspend fun getMatch(): MatchVo {
        try {
            roomDB.matchDao().select()?.let { match ->
                return MatchVo(
                    roomId = match.roomId,
                    round = match.round,
                    isMatchOver = match.isMatchOver,
                    matchStateCode = match.matchState.code,
                )
            }
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_FAIL)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_FAIL)
        }
    }

    override suspend fun getMatchLive(): LiveData<MatchVo> {
        try {
            return roomDB.matchDao().selectLive().map { match ->
                MatchVo(
                    roomId = match.roomId,
                    round = match.round,
                    isMatchOver = match.isMatchOver,
                    matchStateCode = match.matchState.code,
                )
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_FAIL)
        }
    }

    override suspend fun getMyMatchPlayer(): MatchPlayerVo {
        try {
            roomDB.matchPlayerDao().selectMyMatchPlayer()?.let { matchPlayer ->
                return MatchPlayerVo(
                    playerId = matchPlayer.playerId,
                    mongCode = matchPlayer.mongCode,
                    hp = matchPlayer.hp,
                    state = matchPlayer.state.code,
                    isWinner = matchPlayer.isWinner,
                )
            }
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_PLAYER_FAIL)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_PLAYER_FAIL)
        }
    }

    override suspend fun getMyMatchPlayerLive(): LiveData<MatchPlayerVo> {
        try {
            return roomDB.matchPlayerDao().selectMyMatchPlayerLive().map { matchPlayer ->
                MatchPlayerVo(
                    playerId = matchPlayer.playerId,
                    mongCode = matchPlayer.mongCode,
                    hp = matchPlayer.hp,
                    state = matchPlayer.state.code,
                    isWinner = matchPlayer.isWinner,
                )
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_PLAYER_FAIL)
        }
    }

    override suspend fun getOtherMatchPlayer(): MatchPlayerVo {
        try {
            roomDB.matchPlayerDao().selectOtherMatchPlayer()?.let { matchPlayer ->
                return MatchPlayerVo(
                    playerId = matchPlayer.playerId,
                    mongCode = matchPlayer.mongCode,
                    hp = matchPlayer.hp,
                    state = matchPlayer.state.code,
                    isWinner = matchPlayer.isWinner,
                )
            }
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_PLAYER_FAIL)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_PLAYER_FAIL)
        }
    }

    override suspend fun getOtherMatchPlayerLive(): LiveData<MatchPlayerVo> {
        try {
            return roomDB.matchPlayerDao().selectOtherMatchPlayerLive().map { matchPlayer ->
                MatchPlayerVo(
                    playerId = matchPlayer.playerId,
                    mongCode = matchPlayer.mongCode,
                    hp = matchPlayer.hp,
                    state = matchPlayer.state.code,
                    isWinner = matchPlayer.isWinner,
                )
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
            throw RepositoryException(RepositoryErrorCode.GET_MATCH_PLAYER_FAIL)
        }
    }

    override suspend fun matchWait(mongId: Long) {
        try {
            val res = battleApi.registerMatchWait(mongId = mongId)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.MATCH_WAIT_FAIL)
        }
    }

    override suspend fun matchWaitCancel(mongId: Long) {
        try {
            val res = battleApi.removeMatchWait(mongId = mongId)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.MATCH_WAIT_CANCEL_FAIL)
        }
    }

    override suspend fun matchStart(roomId: String) {
        try {
            roomDB.matchDao().updateMatchState(MatchState.MATCH, roomId)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.MATCH_START_FAIL)
        }
    }

    override suspend fun matchPick(roomId: String) {
        try {
            roomDB.matchDao().updateMatchState(MatchState.PICK, roomId)
            roomDB.matchPlayerDao().updateAllMatchPlayerBattleState(roomId = roomId, state = BattleState.NONE)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.MATCH_START_FAIL)
        }
    }

    override suspend fun matchOver(roomId: String) {
        try {
            roomDB.matchDao().updateMatchState(MatchState.OVER, roomId)
        } catch (e: RuntimeException) {
            throw RepositoryException(RepositoryErrorCode.MATCH_START_FAIL)
        }
    }
}