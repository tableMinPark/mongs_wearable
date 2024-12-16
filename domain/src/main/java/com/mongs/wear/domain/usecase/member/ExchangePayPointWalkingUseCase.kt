package com.mongs.wear.domain.usecase.member

import com.mongs.wear.domain.repositroy.PlayerRepository
import javax.inject.Inject

class ExchangePayPointWalkingUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    suspend operator fun invoke(mongId: Long, walkingCount: Int) {
        playerRepository.exchangePayPointWalking(mongId = mongId, walkingCount = walkingCount)

        val startStepCount = playerRepository.getStartStepCount()
        playerRepository.setStartStepCount(stepCount = startStepCount + walkingCount)

        val nowWalkingCount = playerRepository.getWalkingCount()
        playerRepository.setWalkingCount(walkingCount = nowWalkingCount - walkingCount)

    }
}