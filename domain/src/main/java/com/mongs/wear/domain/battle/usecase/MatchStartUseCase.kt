package com.mongs.wear.domain.battle.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.battle.exception.MatchStartException
import com.mongs.wear.domain.battle.repository.BattleRepository
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchStartUseCase @Inject constructor(
    private val battleRepository: BattleRepository,
) : BaseNoParamUseCase<Unit>() {

    override suspend fun execute() {

        withContext(Dispatchers.IO) {
//        val match = battleRepository.getMatch()
//        battleRepository.matchStart(match.roomId)
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw MatchStartException()
    }
}