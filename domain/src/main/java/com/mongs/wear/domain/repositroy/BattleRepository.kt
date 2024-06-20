package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.vo.MatchPlayerVo
import com.mongs.wear.domain.vo.MatchVo

interface BattleRepository {
    suspend fun getMatch(): MatchVo
    suspend fun getMatchLive(): LiveData<MatchVo>
    suspend fun getMyMatchPlayer(): MatchPlayerVo
    suspend fun getMyMatchPlayerLive(): LiveData<MatchPlayerVo>
    suspend fun getOtherMatchPlayer(): MatchPlayerVo
    suspend fun getOtherMatchPlayerLive(): LiveData<MatchPlayerVo>
    suspend fun matchWait(mongId: Long)
    suspend fun matchWaitCancel(mongId: Long)
    suspend fun matchStart(roomId: String)
    suspend fun matchPick(roomId: String)
    suspend fun matchOver(roomId: String)
}