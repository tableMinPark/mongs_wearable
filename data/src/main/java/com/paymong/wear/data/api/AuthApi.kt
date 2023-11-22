package com.paymong.wear.data.api

import com.paymong.wear.data.model.request.LoginReqModel
import com.paymong.wear.data.model.response.LoginResModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body loginReqModel : LoginReqModel) : Response<LoginResModel>
}