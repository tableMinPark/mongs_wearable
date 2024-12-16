package com.mongs.wear.data_.auth.api

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data_.auth.dto.request.LoginRequestDto
import com.mongs.wear.data_.auth.dto.request.LogoutRequestDto
import com.mongs.wear.data_.auth.dto.request.ReissueRequestDto
import com.mongs.wear.data_.auth.dto.response.LoginResponseDto
import com.mongs.wear.data_.auth.dto.response.ReissueResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto) : Response<ResponseDto<LoginResponseDto>>

    @POST("/auth/reissue")
    suspend fun reissue(@Body reissueRequestDto: ReissueRequestDto) : Response<ResponseDto<ReissueResponseDto>>

    @POST("/auth/logout")
    suspend fun logout(@Body logoutRequestDto: LogoutRequestDto) : Response<ResponseDto<Void>>
}