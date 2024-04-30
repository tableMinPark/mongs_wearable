package com.paymong.wear.data.api.feedback

import com.paymong.wear.data.api.feedback.dto.request.RegisterFeedbackReqDto
import com.paymong.wear.data.api.feedback.dto.response.RegisterFeedbackResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApi {
    @POST("/feedback")
    suspend fun registerFeedback(@Body registerFeedbackReqDto: RegisterFeedbackReqDto) : Response<RegisterFeedbackResDto>
}