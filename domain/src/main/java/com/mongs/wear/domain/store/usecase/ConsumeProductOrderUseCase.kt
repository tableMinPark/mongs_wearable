package com.mongs.wear.domain.store.usecase

import com.mongs.wear.domain.store.repository.StoreRepository
import javax.inject.Inject

class ConsumeProductOrderUseCase @Inject constructor(
    private val storeRepository: StoreRepository,
) {
    suspend operator fun invoke(productId: String, orderId: String, purchaseToken: String) {
        storeRepository.consumeProductOrder(
            productId = productId,
            orderId = orderId,
            purchaseToken = purchaseToken
        )
    }
}