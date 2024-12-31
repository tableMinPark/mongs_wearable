package com.mongs.wear.data.user.repository

import android.util.Log
import com.mongs.wear.data.user.api.StoreApi
import com.mongs.wear.data.user.dto.request.ConsumeProductOrderRequestDto
import com.mongs.wear.data.user.exception.ConsumeProductOrderException
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

    override suspend fun consumeProductOrder(productId: String, orderId: String, purchaseToken: String) {

        val response = storeApi.consumeOrder(
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