package com.mongs.wear.domain.player.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.player.exception.ExchangeStarPointException
import com.mongs.wear.domain.player.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeStarPointUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) : BaseParamUseCase<ExchangeStarPointUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {

            playerRepository.exchangeStarPoint(
                mongId = param.mongId,
                starPoint = param.starPoint,
            )
        }
    }

    data class Param(

        val mongId: Long,

        val starPoint: Int,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw ExchangeStarPointException()
        }
    }
}