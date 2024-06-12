package com.mongs.wear.domain.usecase.configure

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class SetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke() {
        try {
            deviceRepository.setBackgroundMapCode(code = "MP000")
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
    suspend operator fun invoke(code: String) {
        try {
            deviceRepository.setBackgroundMapCode(code = code)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}