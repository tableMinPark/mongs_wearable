package com.paymong.wear.domain.repository

import com.paymong.wear.domain.dto.request.LoginReqDto

interface AuthRepository {
    suspend fun login(loginReqDto: LoginReqDto)
}