package com.mongs.wear.data_.manager.module

import com.mongs.wear.data_.manager.client.ManagementMqttClientImpl
import com.mongs.wear.domain.client.ManagementMqttClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagementMqttClientModule {

    @Binds
    @Singleton
    abstract fun bindManagementMqttClient(client: ManagementMqttClientImpl) : ManagementMqttClient
}