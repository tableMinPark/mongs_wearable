package com.mongs.wear.domain.device.usecase

import androidx.lifecycle.LiveData
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.device.exception.GetBackgroundMapCodeException
import com.mongs.wear.domain.device.repository.DeviceRepository
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
) : BaseNoParamUseCase<LiveData<String>>() {

    override suspend fun execute(): LiveData<String> {

        return withContext(Dispatchers.IO) {
            deviceRepository.getBgMapTypeCodeLive()
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw GetBackgroundMapCodeException()
        }
    }
}