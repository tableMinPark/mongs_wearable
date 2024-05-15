package com.paymong.wear.data.repository.common

import androidx.lifecycle.LiveData
import com.paymong.wear.data.dataStore.ConfigureDataStore
import com.paymong.wear.domain.repository.common.ConfigureRepository
import javax.inject.Inject

class ConfigureRepositoryImpl @Inject constructor(
    private val configureDataStore: ConfigureDataStore
) : ConfigureRepository {
    override suspend fun setSound(sound: Float) {
        configureDataStore.modifySound(sound = sound)
    }

    override suspend fun getSound(): LiveData<Float> {
        return configureDataStore.findSound()
    }

    override suspend fun setBackgroundMapCode(mapCode: String) {
        configureDataStore.modifyBackgroundMapCode(backgroundMapCode = mapCode)
    }

    override suspend fun getBackgroundMapCode(): LiveData<String> {
        return configureDataStore.findBackgroundMapCode()
    }
}