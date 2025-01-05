package com.mongs.wear.domain.device.usecase

import androidx.lifecycle.LiveData
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.device.exception.GetNetworkException
import com.mongs.wear.domain.device.repository.DeviceRepository
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNetworkUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
) : BaseNoParamUseCase<LiveData<Boolean>>() {

    override suspend fun execute(): LiveData<Boolean> {

        return withContext(Dispatchers.IO) {
            deviceRepository.getNetworkLive()
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)
        throw GetNetworkException()
    }
}