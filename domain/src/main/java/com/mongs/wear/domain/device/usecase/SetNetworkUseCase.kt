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
) : BaseParamUseCase<SetNetworkUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        return withContext(Dispatchers.IO) {
            deviceRepository.setNetwork(network = param.network)
        }
    }

    data class Param(
        val network: Boolean
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw SetNetworkException()
        }
    }
}