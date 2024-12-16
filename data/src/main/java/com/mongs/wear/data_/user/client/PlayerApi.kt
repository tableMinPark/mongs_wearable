package com.mongs.wear.data_.user.client

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data_.user.dto.request.ChargeStarPointRequestDto
import com.mongs.wear.data_.user.dto.request.ChargeWalkingRequestDto
import com.mongs.wear.data_.user.dto.request.ExchangeStarPointRequestDto
import com.mongs.wear.data_.user.dto.request.ExchangeWalkingRequestDto
import com.mongs.wear.data_.user.dto.response.GetPlayerResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PlayerApi {

    @GET("/user/player/")
    suspend fun getPlayer() : Response<ResponseDto<GetPlayerResponseDto>>

    @PATCH("/user/player/slot")
    suspend fun increaseSlot() : Response<ResponseDto<Void>>

    @POST("/user/player/charge/starPoint")
    suspend fun chargeStarPoint(@Body chargeStarPointRequestDto: ChargeStarPointRequestDto) : Response<ResponseDto<Void>>

    @POST("/user/player/exchange/starPoint")
    suspend fun exchangeStarPoint(@Body exchangeStarPointRequestDto: ExchangeStarPointRequestDto) : Response<ResponseDto<Void>>

    @PATCH("/user/player/charge/walking")
    suspend fun chargeWalking(@Body chargeWalkingRequestDto: ChargeWalkingRequestDto) : Response<ResponseDto<Void>>

    @POST("/user/player/exchange/walking")
    suspend fun exchangeWalking(@Body exchangeWalkingRequestDto: ExchangeWalkingRequestDto) : Response<ResponseDto<Void>>
}