package com.mongs.wear.domain.usecase.member

import com.mongs.wear.domain.repositroy.PlayerRepository
import javax.inject.Inject

class ExchangeWalkingCountUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    suspend operator fun invoke(mongId: Long, walkingCount: Int) {
        playerRepository.exchangeWalkingCount(mongId = mongId, walkingCount = walkingCount)
    }
}