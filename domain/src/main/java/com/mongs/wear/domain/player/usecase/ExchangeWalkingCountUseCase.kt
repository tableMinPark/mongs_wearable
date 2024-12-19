package com.mongs.wear.domain.player.usecase

import com.mongs.wear.domain.player.repository.PlayerRepository
import javax.inject.Inject

class ExchangeWalkingCountUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {

    suspend operator fun invoke(mongId: Long, walkingCount: Int) {
        playerRepository.exchangeWalking(mongId = mongId, walkingCount = walkingCount)
    }
}