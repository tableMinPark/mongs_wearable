package com.mongs.wear.domain.management.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.management.exception.SleepingMongException
import com.mongs.wear.domain.management.repository.ManagementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SleepingMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) : BaseParamUseCase<SleepingMongUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {
            managementRepository.sleepingMong(mongId = param.mongId)
        }
    }

    data class Param(
        val mongId: Long,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw SleepingMongException()
        }
    }
}