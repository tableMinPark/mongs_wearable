package com.mongs.wear.data.user.module

import com.mongs.wear.data.user.repository.CollectionRepositoryImpl
import com.mongs.wear.data.user.repository.FeedbackRepositoryImpl
import com.mongs.wear.data.user.repository.PlayerRepositoryImpl
import com.mongs.wear.domain.repositroy.CollectionRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCollectionRepository(repository: CollectionRepositoryImpl) : CollectionRepository

    @Binds
    @Singleton
    abstract fun bindFeedbackRepository(repository: FeedbackRepositoryImpl) : FeedbackRepository

    @Binds
    @Singleton
    abstract fun bindPlayerRepository(repository: PlayerRepositoryImpl) : PlayerRepository
}