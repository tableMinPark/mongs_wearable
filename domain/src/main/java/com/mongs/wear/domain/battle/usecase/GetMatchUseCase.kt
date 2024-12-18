package com.mongs.wear.domain.battle.usecase

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.battle.repository.BattleRepository
import com.mongs.wear.domain.battle.vo.MatchVo
import javax.inject.Inject

class GetMatchUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(): LiveData<MatchVo> {
        return battleRepository.getMatchLive()
    }
}