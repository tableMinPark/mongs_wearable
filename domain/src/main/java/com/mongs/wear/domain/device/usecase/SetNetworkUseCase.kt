package com.mongs.wear.domain.device.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.device.exception.SetNetworkException
import com.mongs.wear.domain.device.repository.DeviceRepository
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetNetworkUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
) : BaseParamUseCase<Boolean, Unit>() {

    override suspend fun execute(network: Boolean) {

        return withContext(Dispatchers.IO) {
            deviceRepository.setNetwork(network = network)
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw SetNetworkException()
    }
}