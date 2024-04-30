package com.paymong.wear.data.api.auth

import com.paymong.wear.data.api.auth.dto.request.LoginReqDto
import com.paymong.wear.data.api.auth.dto.request.LogoutReqDto
import com.paymong.wear.data.api.auth.dto.request.ReissueReqDto
import com.paymong.wear.data.api.auth.dto.response.LoginResDto
import com.paymong.wear.data.api.auth.dto.response.LogoutResDto
import com.paymong.wear.data.api.auth.dto.response.ReissueResDto
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