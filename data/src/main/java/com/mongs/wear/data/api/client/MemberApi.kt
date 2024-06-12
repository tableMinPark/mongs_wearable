package com.mongs.wear.data.api.client

import com.mongs.wear.data.dto.member.req.ChargeStarPointReqDto
import com.mongs.wear.data.dto.member.req.ExchangePayPointReqDto
import com.mongs.wear.data.dto.member.req.ExchangePayPointWalkingReqDto
import com.mongs.wear.data.dto.member.res.ChargeStarPointResDto
import com.mongs.wear.data.dto.member.res.FindChargeItemResDto
import com.mongs.wear.data.dto.member.res.FindExchangeItemResDto
import com.mongs.wear.data.dto.member.res.FindMemberResDto
import com.mongs.wear.data.dto.member.res.BuySlotResDto
import com.mongs.wear.data.dto.member.res.ExchangePayPointResDto
import com.mongs.wear.data.dto.member.res.ExchangePayPointWalkingResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MemberApi {
    @GET("/member")
    suspend fun findMember() : Response<FindMemberResDto>
    @GET("/member/charge")
    suspend fun findChargeItem() : Response<List<FindChargeItemResDto>>
    @GET("/member/exchange")
    suspend fun findExchangeItem() : Response<List<FindExchangeItemResDto>>
    @PUT("/member/slot")
    suspend fun buySlot() : Response<BuySlotResDto>
    @POST("/member/charge/starPoint")
    suspend fun chargeStarPoint(@Body chargeStarPointReqDto: ChargeStarPointReqDto) : Response<ChargeStarPointResDto>
    @PUT("/member/exchange/payPoint")
    suspend fun exchangePayPoint(@Body exchangePayPointReqDto: ExchangePayPointReqDto) : Response<ExchangePayPointResDto>
    @PUT("/member/exchange/payPoint/walking")
    suspend fun exchangePayPointWalking(@Body exchangePayPointWalkingReqDto: ExchangePayPointWalkingReqDto) : Response<ExchangePayPointWalkingResDto>
}