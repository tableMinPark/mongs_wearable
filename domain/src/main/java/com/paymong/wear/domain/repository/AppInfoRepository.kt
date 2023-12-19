package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData

interface AppInfoRepository {
    fun initAppInfo()
    fun modifyMapCode(mapCode: String)
    fun findMapCode(): LiveData<String>
}