package com.paymong.wear.data.api.client

import com.paymong.wear.data.dto.collection.res.FindMapCollectionResDto
import com.paymong.wear.data.dto.collection.res.FindMongCollectionResDto
import retrofit2.Response
import retrofit2.http.GET

interface CollectionApi {
    @GET("/collection/map")
    suspend fun findMapCollection() : Response<List<FindMapCollectionResDto>>
    @GET("/collection/mong")
    suspend fun findMongCollection() : Response<List<FindMongCollectionResDto>>
}