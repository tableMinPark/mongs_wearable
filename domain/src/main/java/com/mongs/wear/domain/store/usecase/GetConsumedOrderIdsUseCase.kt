package com.mongs.wear.domain.store.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.store.exception.GetConsumedOrderIdsException
import com.mongs.wear.domain.store.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetConsumedOrderIdsUseCase @Inject constructor(
    private val storeRepository: StoreRepository,
) : BaseParamUseCase<GetConsumedOrderIdsUseCase.Param, List<String>>() {

    override suspend fun execute(param: Param): List<String> {

        return withContext(Dispatchers.IO) {
            storeRepository.getConsumedOrderIds(
                orderIds = param.orderIds,
            )
        }
    }

    data class Param(
        val orderIds: List<String>,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw GetConsumedOrderIdsException()
        }
    }
}