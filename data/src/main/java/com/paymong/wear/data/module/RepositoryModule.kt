package com.paymong.wear.data.module

import com.paymong.wear.data.repository.AuthRepositoryImpl
import com.paymong.wear.domain.repository.AuthRepository
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
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl) : AuthRepository
}