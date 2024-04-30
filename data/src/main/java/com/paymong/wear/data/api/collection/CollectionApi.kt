package com.paymong.wear.data.api.collection

import com.paymong.wear.data.api.collection.dto.response.FindMapCollectionResDto
import com.paymong.wear.data.api.collection.dto.response.FindMongCollectionResDto
import retrofit2.Response
import retrofit2.http.GET

interface CollectionApi {
    @GET("/collection/map")
    suspend fun findMapCollection() : Response<List<FindMapCollectionResDto>>
    @GET("/collection/mong")
    suspend fun findMongCollection() : Response<List<FindMongCollectionResDto>>
}