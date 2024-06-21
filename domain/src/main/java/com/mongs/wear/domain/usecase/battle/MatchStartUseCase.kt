package com.mongs.wear.domain.usecase.battle

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.BattleRepository
import javax.inject.Inject

class MatchStartUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) {
    suspend operator fun invoke() {
        try {
            val match = battleRepository.getMatch()
            battleRepository.matchStart(match.roomId)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}