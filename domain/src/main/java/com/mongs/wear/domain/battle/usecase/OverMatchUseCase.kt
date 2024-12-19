package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.domain.battle.repository.BattleRepository
import javax.inject.Inject

class OverMatchUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke(roomId: Long) {

        battleRepository.updateOverMatch(roomId = roomId)
    }
}