package com.mongs.wear.domain.player.usecase

import com.mongs.wear.domain.player.repository.StoreRepository
import javax.inject.Inject

class GetProductIdsUseCase @Inject constructor(
    private val storeRepository: StoreRepository,
) {
    suspend operator fun invoke(): List<String> = storeRepository.getProductIds().map { productId ->
        productId.lowercase()
    }
}