package com.mongs.wear.domain.store.repository

interface StoreRepository {

    suspend fun getProductIds(): List<String>

    suspend fun getConsumedOrderIds(orderIds: List<String>): List<String>

    suspend fun consumeProductOrder(productId: String, orderId: String, purchaseToken: String)
}