package com.mongs.wear.data.api.client

import com.mongs.wear.data.dto.common.req.FindCodeReqDto
import com.mongs.wear.data.dto.common.req.FindVersionReqDto
import com.mongs.wear.data.dto.common.res.FindCodeResDto
import com.mongs.wear.data.dto.common.res.FindVersionResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CommonApi {
    @POST("/common/version")
    suspend fun findVersion(@Body findVersionReqDto: FindVersionReqDto) : Response<FindVersionResDto>
    @POST("/common/code")
    suspend fun findCode(@Body findCodeReqDto: FindCodeReqDto) : Response<FindCodeResDto>
}