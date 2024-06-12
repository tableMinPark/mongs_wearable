package com.mongs.wear.domain.repositroy

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.vo.BattleRoomVo

interface BattleRepository {
    suspend fun getBattleRoom(): LiveData<BattleRoomVo>
    suspend fun matchSearch(mongId: Long)
    suspend fun roundPick(direction: String)
    suspend fun matchExit(mongId: Long)
}