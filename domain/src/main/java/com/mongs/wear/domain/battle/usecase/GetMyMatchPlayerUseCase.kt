package com.mongs.wear.domain.battle.usecase

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.battle.repository.BattleRepository
import com.mongs.wear.domain.battle.vo.MatchPlayerVo
import javax.inject.Inject

class GetMyMatchPlayerUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(): LiveData<MatchPlayerVo> {
        return battleRepository.getMyMatchPlayerLive()
    }
}