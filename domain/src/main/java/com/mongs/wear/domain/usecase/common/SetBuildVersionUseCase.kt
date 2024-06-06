package com.mongs.wear.domain.usecase.common

import com.mongs.wear.domain.repositroy.DeviceRepository
import javax.inject.Inject

class SetBuildVersionUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
){
    suspend operator fun invoke(buildVersion: String) {
        if (deviceRepository.getBuildVersion() != buildVersion) {
            deviceRepository.setBuildVersion(buildVersion = buildVersion)
        }
    }
}