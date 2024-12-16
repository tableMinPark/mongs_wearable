package com.mongs.wear.data_.user.client

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data_.user.dto.response.GetCollectionMapResponseDto
import com.mongs.wear.data_.user.dto.response.GetCollectionMongResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CollectionApi {

    @GET("/user/collection/map")
    suspend fun getCollectionMaps() : Response<ResponseDto<List<GetCollectionMapResponseDto>>>

    @GET("/user/collection/mong")
    suspend fun getCollectionMongs() : Response<ResponseDto<List<GetCollectionMongResponseDto>>>
}