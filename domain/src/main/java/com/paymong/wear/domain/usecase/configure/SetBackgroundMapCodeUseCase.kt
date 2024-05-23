package com.paymong.wear.domain.usecase.configure

import com.paymong.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class SetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke() {
        deviceRepository.setBackgroundMapCode(code = "MP000")
    }
    suspend operator fun invoke(code: String) {
        deviceRepository.setBackgroundMapCode(code = code)
    }
}