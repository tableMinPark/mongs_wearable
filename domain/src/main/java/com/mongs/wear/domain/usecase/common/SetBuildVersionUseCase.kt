package com.mongs.wear.domain.usecase.common

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import javax.inject.Inject

class SetBuildVersionUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val feedbackRepository: FeedbackRepository,
){
    suspend operator fun invoke(buildVersion: String) {
        try {
            if (deviceRepository.getBuildVersion() != buildVersion) {
                deviceRepository.setBuildVersion(buildVersion = buildVersion)
            }
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "GetMapCollectionsUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}