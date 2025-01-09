package com.mongs.wear.domain.management.usecase

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.management.exception.StrokeMongException
import com.mongs.wear.domain.management.repository.ManagementRepository
import javax.inject.Inject

class StrokeMongUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) : BaseParamUseCase<StrokeMongUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {
        managementRepository.strokeMong(mongId = param.mongId)
    }

    data class Param(
        val mongId: Long,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {

            DataErrorCode.DATA_MANAGER_MANAGEMENT_STROKE_MONG -> {

                val expirationSeconds = exception.result["expirationSeconds"]?.toString()
                    ?.toDouble()?.toLong()
                    ?: 0

                throw StrokeMongException(expirationSeconds = expirationSeconds)
            }

            else -> throw StrokeMongException()
        }
    }
}