package com.mongs.wear.data.api.client

import com.mongs.wear.data.dto.auth.req.LoginReqDto
import com.mongs.wear.data.dto.auth.req.LogoutReqDto
import com.mongs.wear.data.dto.auth.req.ReissueReqDto
import com.mongs.wear.data.dto.auth.res.LoginResDto
import com.mongs.wear.data.dto.auth.res.LogoutResDto
import com.mongs.wear.data.dto.auth.res.ReissueResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body loginReqDto: LoginReqDto) : Response<LoginResDto>
    @POST("/auth/reissue")
    suspend fun reissue(@Body reissueReqDto: ReissueReqDto) : Response<ReissueResDto>
    @POST("/auth/logout")
    suspend fun logout(@Body logoutReqDto: LogoutReqDto) : Response<LogoutResDto>
}