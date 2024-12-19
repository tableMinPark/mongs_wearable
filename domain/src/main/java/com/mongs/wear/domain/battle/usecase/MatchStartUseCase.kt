package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.domain.battle.repository.BattleRepository
import javax.inject.Inject

class MatchStartUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
//        val match = battleRepository.getMatch()
//        battleRepository.matchStart(match.roomId)
    }
}