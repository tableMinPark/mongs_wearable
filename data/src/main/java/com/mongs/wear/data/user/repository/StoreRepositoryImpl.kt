package com.mongs.wear.data.user.repository

import com.mongs.wear.data.user.api.StoreApi
import com.mongs.wear.data.user.dto.request.ConsumeProductOrderRequestDto
import com.mongs.wear.data.user.dto.request.GetConsumedOrderIdsRequestDto
import com.mongs.wear.data.user.exception.ConsumeProductOrderException
import com.mongs.wear.data.user.exception.GetConsumedOrderIdsException
import com.mongs.wear.data.user.exception.GetProductIdsException
import com.mongs.wear.domain.store.repository.StoreRepository
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeApi: StoreApi,
) : StoreRepository {

    override suspend fun getProductIds(): List<String> {

        val response = storeApi.getProducts()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                return body.result.map { it.productId }
            } ?: run {
                return ArrayList()
            }
        }

        throw GetProductIdsException()
    }

    override suspend fun getConsumedOrderIds(orderIds: List<String>): List<String> {

        val response = storeApi.getConsumedOrderIds(GetConsumedOrderIdsRequestDto(orderIds))

        if (response.isSuccessful) {
            response.body()?.let { body ->
                return body.result.orderIds
            } ?: run {
                return ArrayList()
            }
        }

        throw GetConsumedOrderIdsException()
    }

    override suspend fun consumeProductOrder(productId: String, orderId: String, purchaseToken: String) {

        val response = storeApi.consumeProductOrder(
            ConsumeProductOrderRequestDto(
                productId = productId,
                orderId = orderId,
                purchaseToken = purchaseToken
            )
        )

        if (!response.isSuccessful) {
            throw ConsumeProductOrderException(
                productId = productId,
                orderId = orderId,
                purchaseToken = purchaseToken
            )
        }
    }
}