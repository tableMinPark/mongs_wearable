package com.mongs.wear.data.user.api

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data.user.dto.request.ConsumeProductOrderRequestDto
import com.mongs.wear.data.user.dto.response.GetProductsResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StoreApi {

    @GET("user/store/product")
    suspend fun getProducts() : Response<ResponseDto<List<GetProductsResponseDto>>>

    @POST("user/store/order")
    suspend fun consumeOrder(@Body consumeProductOrderRequestDto: ConsumeProductOrderRequestDto) : Response<ResponseDto<Void>>
}