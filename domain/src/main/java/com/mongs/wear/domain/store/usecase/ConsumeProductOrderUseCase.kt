package com.mongs.wear.domain.store.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.store.exception.ConsumeProductOrderException
import com.mongs.wear.domain.store.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConsumeProductOrderUseCase @Inject constructor(
    private val storeRepository: StoreRepository,
) : BaseParamUseCase<ConsumeProductOrderUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {
            storeRepository.consumeProductOrder(
                productId = param.productId,
                orderId = param.orderId,
                purchaseToken = param.purchaseToken
            )
        }
    }

    data class Param(

        val productId: String,

        val orderId: String,

        val purchaseToken: String,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw ConsumeProductOrderException()
        }
    }
}