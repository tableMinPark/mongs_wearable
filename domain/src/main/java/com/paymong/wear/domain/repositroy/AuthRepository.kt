package com.paymong.wear.domain.repositroy

import com.paymong.wear.domain.model.LoginModel
import com.paymong.wear.domain.model.ReissueModel

interface AuthRepository {
    suspend fun login(deviceId: String, email: String, name: String): LoginModel
    suspend fun logout(refreshToken: String): Long
    suspend fun reissue(refreshToken: String): ReissueModel
}