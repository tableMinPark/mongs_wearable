package com.mongs.wear.domain.player.usecase

import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.domain.player.repository.PlayerRepository
import javax.inject.Inject

class ExchangeWalkingCountUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val playerRepository: PlayerRepository,
) {

    suspend operator fun invoke(mongId: Long, walkingCount: Int) {

        val deviceBootedDt = appRepository.getDeviceBootedDt()

        playerRepository.exchangeWalking(mongId = mongId, walkingCount = walkingCount, deviceBootedDt = deviceBootedDt)
    }
}