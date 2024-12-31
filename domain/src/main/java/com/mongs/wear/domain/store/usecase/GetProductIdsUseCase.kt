package com.mongs.wear.domain.store.usecase

import com.mongs.wear.domain.store.repository.StoreRepository
import javax.inject.Inject

class GetProductIdsUseCase @Inject constructor(
    private val storeRepository: StoreRepository,
) {
    suspend operator fun invoke(): List<String> = storeRepository.getProductIds().map { productId ->
        productId.lowercase()
    }
}