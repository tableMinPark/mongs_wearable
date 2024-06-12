package com.mongs.wear.domain.usecase.common

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class LandingUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
){
    suspend operator fun invoke(buildVersion: String) {
        try {
            if (deviceRepository.getBuildVersion() != buildVersion) {
                deviceRepository.setBuildVersion(buildVersion = buildVersion)
            }
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}