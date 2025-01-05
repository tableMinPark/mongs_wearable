package com.mongs.wear.data.device.module

import com.mongs.wear.data.device.repository.DeviceRepositoryImpl
import com.mongs.wear.domain.device.repository.DeviceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDeviceRepository(repository: DeviceRepositoryImpl): DeviceRepository
}