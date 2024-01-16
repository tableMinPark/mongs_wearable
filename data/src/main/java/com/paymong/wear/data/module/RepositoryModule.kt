package com.paymong.wear.data.module

import com.paymong.wear.data.repository.AppInfoRepositoryImpl
import com.paymong.wear.data.repository.AuthRepositoryImpl
import com.paymong.wear.data.repository.MqttRepositoryImpl
import com.paymong.wear.data.repository.SlotRepositoryImpl
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.AuthRepository
import com.paymong.wear.domain.repository.MqttRepository
import com.paymong.wear.domain.repository.SlotRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl) : AuthRepository
    @Binds
    @Singleton
    abstract fun bindAppInfoRepository(repository: AppInfoRepositoryImpl) : AppInfoRepository
    @Binds
    @Singleton
    abstract fun bindMongRepository(repository: SlotRepositoryImpl) : SlotRepository
    @Binds
    @Singleton
    abstract fun bindMqttRepository(repository: MqttRepositoryImpl) : MqttRepository
}