package com.mongs.wear.domain.battle.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.battle.repository.BattleRepository
import com.mongs.wear.domain.battle.vo.MatchVo
import javax.inject.Inject

class GetMatchUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {

    suspend operator fun invoke(): LiveData<MatchVo> = battleRepository.getMatchLive().map { matchModel ->
        MatchVo(
            roomId = matchModel.roomId,
            round = matchModel.round,
            isLastRound = matchModel.isLastRound,
            stateCode = matchModel.stateCode,
        )
    }
}