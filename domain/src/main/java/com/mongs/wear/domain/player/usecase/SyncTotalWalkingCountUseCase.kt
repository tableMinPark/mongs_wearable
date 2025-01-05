package com.mongs.wear.domain.player.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.device.repository.DeviceRepository
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.player.exception.SyncTotalWalkingCountException
import com.mongs.wear.domain.player.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncTotalWalkingCountUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val playerRepository: PlayerRepository,
) : BaseParamUseCase<SyncTotalWalkingCountUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {

            val deviceId = deviceRepository.getDeviceId()

            val deviceBootedDt = deviceRepository.getDeviceBootedDt()

            playerRepository.syncTotalWalkingCount(
                deviceId = deviceId,
                totalWalkingCount = param.totalWalkingCount,
                deviceBootedDt = deviceBootedDt
            )
        }
    }

    data class Param(
        val totalWalkingCount: Int,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw SyncTotalWalkingCountException()
    }
}