package com.mongs.wear.domain.usecase.configure

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class GetBackgroundMapCodeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(): LiveData<String> {
        try {
            return deviceRepository.getBackgroundMapCode()
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}