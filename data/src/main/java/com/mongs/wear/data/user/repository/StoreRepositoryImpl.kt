package com.mongs.wear.data.user.repository

import com.mongs.wear.data.user.api.StoreApi
import com.mongs.wear.data.user.exception.GetProductIdsException
import com.mongs.wear.domain.player.repository.StoreRepository
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
}