package com.mongs.wear.domain.store.usecase

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseNoParamUseCase
import com.mongs.wear.domain.store.exception.GetProductIdsException
import com.mongs.wear.domain.store.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductIdsUseCase @Inject constructor(
    private val storeRepository: StoreRepository,
) : BaseNoParamUseCase<List<String>>() {

    override suspend fun execute(): List<String> {

        return withContext(Dispatchers.IO) {
            storeRepository.getProductIds().map { productId ->
                productId.lowercase()
            }
        }
    }

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw GetProductIdsException()
        }
    }
}