package com.mongs.wear.data.user.api

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data.user.dto.request.CreateFeedbackRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApi {

    @POST("/user/feedback")
    suspend fun createFeedback(@Body createFeedbackRequestDto: CreateFeedbackRequestDto) : Response<ResponseDto<Void>>
}