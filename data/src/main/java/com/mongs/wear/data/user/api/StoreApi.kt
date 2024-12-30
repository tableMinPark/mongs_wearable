package com.mongs.wear.data.user.api

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data.user.dto.request.ChargeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ConsumeProductOrderRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeWalkingCountRequestDto
import com.mongs.wear.data.user.dto.request.SyncWalkingCountRequestDto
import com.mongs.wear.data.user.dto.response.ExchangeWalkingCountResponseDto
import com.mongs.wear.data.user.dto.response.GetPlayerResponseDto
import com.mongs.wear.data.user.dto.response.GetProductsResponseDto
import com.mongs.wear.data.user.dto.response.SyncWalkingCountResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface StoreApi {

    @GET("user/store/product")
    suspend fun getProducts() : Response<ResponseDto<List<GetProductsResponseDto>>>

    @POST("user/store/order")
    suspend fun consumeOrder(consumeProductOrderRequestDto: ConsumeProductOrderRequestDto) : Response<ResponseDto<Void>>
}