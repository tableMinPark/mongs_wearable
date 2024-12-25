package com.mongs.wear.data.user.api

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data.user.dto.request.ChargeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeStarPointRequestDto
import com.mongs.wear.data.user.dto.request.ExchangeWalkingCountRequestDto
import com.mongs.wear.data.user.dto.request.SyncWalkingCountRequestDto
import com.mongs.wear.data.user.dto.response.ExchangeWalkingCountResponseDto
import com.mongs.wear.data.user.dto.response.GetPlayerResponseDto
import com.mongs.wear.data.user.dto.response.SyncWalkingCountResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PlayerApi {

    @GET("user/player/")
    suspend fun getPlayer() : Response<ResponseDto<GetPlayerResponseDto>>

    @PATCH("user/player/slot")
    suspend fun buySlot() : Response<ResponseDto<Void>>

    @POST("user/player/charge/starPoint")
    suspend fun chargeStarPoint(@Body chargeStarPointRequestDto: ChargeStarPointRequestDto) : Response<ResponseDto<Void>>

    @POST("user/player/exchange/starPoint")
    suspend fun exchangeStarPoint(@Body exchangeStarPointRequestDto: ExchangeStarPointRequestDto) : Response<ResponseDto<Void>>

    @PATCH("user/player/sync/walking")
    suspend fun syncWalkingCount(@Body syncWalkingCountRequestDto: SyncWalkingCountRequestDto) : Response<ResponseDto<SyncWalkingCountResponseDto>>

    @POST("user/player/exchange/walking")
    suspend fun exchangeWalkingCount(@Body exchangeWalkingCountRequestDto: ExchangeWalkingCountRequestDto) : Response<ResponseDto<ExchangeWalkingCountResponseDto>>
}