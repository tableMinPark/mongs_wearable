package com.mongs.wear.domain.auth.repository

interface AuthRepository {

    /**
     * 로그인
     * @return accountId
     */
    suspend fun login(deviceId: String, email: String, name: String) : Long

    /**
     * 로그아웃
     */
    suspend fun logout()
}