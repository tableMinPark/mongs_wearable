package com.paymong.wear.domain.usecase.common

import com.paymong.wear.domain.repositroy.DeviceRepository
import java.util.UUID
import javax.inject.Inject

class SetDeviceIdUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
){
    suspend operator fun invoke() {
        if (deviceRepository.getDeviceId().isBlank()) {
            val newDeviceId = UUID.randomUUID().toString()
            deviceRepository.setDeviceId(deviceId = newDeviceId)
        }
    }
}