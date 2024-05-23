package com.paymong.wear.domain.usecase.configure

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class GetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(): LiveData<String> {
        return deviceRepository.getBackgroundMapCode()
    }
}