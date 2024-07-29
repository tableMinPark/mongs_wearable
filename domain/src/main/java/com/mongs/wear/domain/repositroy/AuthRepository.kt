package com.mongs.wear.domain.repositroy

import com.mongs.wear.domain.model.LoginModel

interface AuthRepository {
    suspend fun login(deviceId: String, email: String, name: String): LoginModel
    suspend fun logout(refreshToken: String): Long
}