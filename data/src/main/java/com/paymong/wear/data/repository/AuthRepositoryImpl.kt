package com.paymong.wear.data.repository

import android.util.Log
import com.paymong.wear.data.api.AuthApi
import com.paymong.wear.data.model.request.LoginReqModel
import com.paymong.wear.domain.dto.request.LoginReqDto
import com.paymong.wear.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(loginReqDto: LoginReqDto) {
        val request = LoginReqModel(loginReqDto.playerId)

        val response = api.login(request)
        Log.e("test", response.toString())
    }
}