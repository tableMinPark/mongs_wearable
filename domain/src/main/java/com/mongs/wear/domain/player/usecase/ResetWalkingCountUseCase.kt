package com.mongs.wear.domain.player.usecase

import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.domain.player.repository.PlayerRepository
import javax.inject.Inject

class ResetWalkingCountUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val playerRepository: PlayerRepository,
) {

    suspend operator fun invoke() : Boolean {
        try {

            val deviceId = appRepository.getDeviceId()

            val deviceBootedDt = appRepository.getDeviceBootedDt()

            playerRepository.resetWalkingCount(deviceId = deviceId, deviceBootedDt = deviceBootedDt)

            return true

        } catch (_: Exception) {
            return false
        }
    }
}