package com.mongs.wear.domain.player.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.player.exception.SetTotalWalkingCountException
import com.mongs.wear.domain.player.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetTotalWalkingCountUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) : BaseParamUseCase<SetTotalWalkingCountUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {
            playerRepository.setTotalWalkingCount(totalWalkingCount = param.totalWalkingCount)
        }
    }

    data class Param(
        val totalWalkingCount: Int,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw SetTotalWalkingCountException()
    }
}