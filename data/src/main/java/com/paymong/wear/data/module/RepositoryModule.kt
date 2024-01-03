package com.paymong.wear.data.module

import com.paymong.wear.data.repository.AppInfoRepositoryImpl
import com.paymong.wear.data.repository.AuthRepositoryImpl
import com.paymong.wear.data.repository.FeedInfoRepositoryImpl
import com.paymong.wear.data.repository.MongRepositoryImpl
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.AuthRepository
import com.paymong.wear.domain.repository.FeedInfoRepository
import com.paymong.wear.domain.repository.MongRepository
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
    @Binds
    @Singleton
    abstract fun bindAppInfoRepository(repository: AppInfoRepositoryImpl) : AppInfoRepository
    @Binds
    @Singleton
    abstract fun bindMongRepository(repository: MongRepositoryImpl) : MongRepository
    @Binds
    @Singleton
    abstract fun bindFeedRepository(repository: FeedInfoRepositoryImpl) : FeedInfoRepository
}