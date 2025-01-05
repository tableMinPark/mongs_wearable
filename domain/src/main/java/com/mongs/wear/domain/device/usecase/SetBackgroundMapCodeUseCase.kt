package com.mongs.wear.domain.device.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.device.exception.SetBackgroundMapCodeException
import com.mongs.wear.domain.device.repository.DeviceRepository
import com.mongs.wear.domain.device.usecase.SetBackgroundMapCodeUseCase.Param
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
) : BaseParamUseCase<Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {
            deviceRepository.setBgMapTypeCode(mapTypeCode = param.mapTypeCode ?: "MP000")
        }
    }

    data class Param(
        val mapTypeCode: String?
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw SetBackgroundMapCodeException()
    }
}