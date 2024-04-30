package com.paymong.wear.data.api.common

import com.paymong.wear.data.api.common.dto.response.FindCodeResDto
import com.paymong.wear.data.api.common.dto.response.FindVersionResDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonApi {
    @GET("/common/version")
    suspend fun findNewestVersion(
        @Query("buildVersion") buildVersion: String,
        @Query("codeIntegrity") codeIntegrity: String
    ) : Response<FindVersionResDto>
    @GET("/common/code")
    suspend fun findCode() : Response<FindCodeResDto>
}