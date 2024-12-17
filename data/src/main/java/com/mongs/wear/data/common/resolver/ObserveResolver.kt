package com.mongs.wear.data.common.resolver

import android.util.Log
import com.mongs.wear.data.activity.dto.response.CreateBattleResponseDto
import com.mongs.wear.data.activity.dto.response.FightBattleResponseDto
import com.mongs.wear.data.activity.dto.response.OverBattleResponseDto
import com.mongs.wear.data.manager.dto.response.MongObserveResponseDto
import com.mongs.wear.data.manager.dto.response.MongStateObserveResponseDto
import com.mongs.wear.data.manager.dto.response.MongStatusObserveResponseDto
import com.mongs.wear.data.user.dto.response.PlayerObserveResponseDto
import javax.inject.Inject

class ObserveResolver @Inject constructor(

) {

    suspend fun updateSearchMatch(createBattleResponseDto: CreateBattleResponseDto) {

        Log.d("ObserveResolver", "$createBattleResponseDto")
    }

    suspend fun updateBattleMatchEnter(fightBattleResponseDto: FightBattleResponseDto) {

        Log.d("ObserveResolver", "$fightBattleResponseDto")
    }

    suspend fun updateBattleMatchOver(overBattleResponseDto: OverBattleResponseDto) {

        Log.d("ObserveResolver", "$overBattleResponseDto")
    }

    suspend fun updateBattleMatchFight(fightBattleResponseDto: FightBattleResponseDto) {

        Log.d("ObserveResolver", "$fightBattleResponseDto")
    }

    suspend fun updateMong(mongObserveResponseDto: MongObserveResponseDto) {

        Log.d("ObserveResolver", "$mongObserveResponseDto")
    }

    suspend fun updateMOngState(mongStateObserveResponseDto: MongStateObserveResponseDto) {

        Log.d("ObserveResolver", "$mongStateObserveResponseDto")
    }

    suspend fun updateMongStatus(mongStatusObserveResponseDto: MongStatusObserveResponseDto) {

        Log.d("ObserveResolver", "$mongStatusObserveResponseDto")
    }

    suspend fun updatePlayer(playerObserveResponseDto: PlayerObserveResponseDto) {

        Log.d("ObserveResolver", "$playerObserveResponseDto")
    }
}