package com.paymong.wear.domain.usecase.configure

import com.paymong.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class GetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(): String {
        return deviceRepository.getBackgroundMapCode()
    }
}