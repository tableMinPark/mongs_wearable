package com.paymong.wear.data.api.member

import com.paymong.wear.data.api.member.dto.response.FindMemberResDto
import com.paymong.wear.data.api.member.dto.response.ModifySlotCountResDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT

interface MemberApi {
    @GET("/member")
    suspend fun findMember() : Response<FindMemberResDto>
    @PUT("/slot")
    suspend fun modifySlotCount() : Response<ModifySlotCountResDto>
}