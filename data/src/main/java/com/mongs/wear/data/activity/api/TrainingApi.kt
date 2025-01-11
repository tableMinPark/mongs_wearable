package com.mongs.wear.data.activity.api

import com.mongs.wear.core.dto.response.ResponseDto
import com.mongs.wear.data.activity.dto.request.TrainingRunnerRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TrainingApi {

    @POST("activity/training/runner/{mongId}")
    suspend fun trainingRunner(@Path("mongId") mongId: Long, @Body trainingRunnerRequestDto: TrainingRunnerRequestDto) : Response<ResponseDto<Void>>
}