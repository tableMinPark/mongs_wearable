package com.paymong.wear.data.api.retrofit

import com.paymong.wear.data.api.model.request.LoginReqModel
import com.paymong.wear.data.api.model.response.LoginResModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body loginReqModel : LoginReqModel) : Response<LoginResModel>
}