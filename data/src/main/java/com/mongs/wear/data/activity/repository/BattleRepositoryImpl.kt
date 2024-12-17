package com.mongs.wear.data.activity.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mongs.wear.domain.repositroy.BattleRepository
import com.mongs.wear.domain.vo.MatchPlayerVo
import com.mongs.wear.domain.vo.MatchVo
import javax.inject.Inject

class BattleRepositoryImpl @Inject constructor(
): BattleRepository {

    override suspend fun getMatch(): MatchVo {
        Log.d("BattleRepositoryImpl", "getMatch")
        return MatchVo()
    }

    override suspend fun getMatchLive(): LiveData<MatchVo> {
        Log.d("BattleRepositoryImpl", "getMatchLive")
        return MutableLiveData(MatchVo())
    }

    override suspend fun getMyMatchPlayer(): MatchPlayerVo {
        Log.d("BattleRepositoryImpl", "getMyMatchPlayer")
        return MatchPlayerVo()
    }

    override suspend fun getMyMatchPlayerLive(): LiveData<MatchPlayerVo> {
        Log.d("BattleRepositoryImpl", "getMyMatchPlayerLive")
        return MutableLiveData(MatchPlayerVo())
    }

    override suspend fun getOtherMatchPlayer(): MatchPlayerVo {
        Log.d("BattleRepositoryImpl", "getOtherMatchPlayer")
        return MatchPlayerVo()
    }

    override suspend fun getOtherMatchPlayerLive(): LiveData<MatchPlayerVo> {
        Log.d("BattleRepositoryImpl", "getOtherMatchPlayerLive")
        return MutableLiveData(MatchPlayerVo())
    }

    override suspend fun matchWait(mongId: Long) {
        Log.d("BattleRepositoryImpl", "matchWait")
    }

    override suspend fun matchWaitCancel(mongId: Long) {
        Log.d("BattleRepositoryImpl", "matchWaitCancel")
    }

    override suspend fun matchStart(roomId: Long) {
        Log.d("BattleRepositoryImpl", "matchStart")
    }

    override suspend fun matchPick(roomId: Long) {
        Log.d("BattleRepositoryImpl", "matchPick")
    }

    override suspend fun matchOver(roomId: Long) {
        Log.d("BattleRepositoryImpl", "matchOver")
    }
}