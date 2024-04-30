package com.paymong.wear.domain.repository.common

import androidx.lifecycle.LiveData

interface ConfigureRepository {
    suspend fun setSound(sound: Float)
    suspend fun getSound(): LiveData<Float>
    suspend fun setBackgroundMapCode(mapCode: String)
    suspend fun getBackgroundMapCode(): LiveData<String>
}