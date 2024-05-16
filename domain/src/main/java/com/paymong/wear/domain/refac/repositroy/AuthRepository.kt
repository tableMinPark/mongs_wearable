package com.paymong.wear.domain.refac.repositroy

import com.paymong.wear.domain.refac.model.LoginModel

interface AuthRepository {
    suspend fun login(deviceId: String, email: String, name: String) : Long
    suspend fun logout() : Long
}