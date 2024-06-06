package com.mongs.wear.data.api.client

import com.mongs.wear.data.dto.feedback.req.RegisterFeedbackReqDto
import com.mongs.wear.data.dto.feedback.res.RegisterFeedbackResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApi {
    @POST("/feedback")
    suspend fun registerFeedback(@Body registerFeedbackReqDto: RegisterFeedbackReqDto) : Response<RegisterFeedbackResDto>
}