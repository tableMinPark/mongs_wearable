package com.mongs.wear.domain.store.usecase

import com.mongs.wear.domain.store.repository.StoreRepository
import javax.inject.Inject

class GetConsumedOrderIdsUseCase @Inject constructor(
    private val storeRepository: StoreRepository,
) {
    suspend operator fun invoke(orderIds: List<String>): List<String> = storeRepository.getConsumedOrderIds(
        orderIds = orderIds,
    );
}