package com.mongs.wear.data.user.module

import com.mongs.wear.data.user.repository.CollectionRepositoryImpl
import com.mongs.wear.data.user.repository.FeedbackRepositoryImpl
import com.mongs.wear.data.user.repository.PlayerRepositoryImpl
import com.mongs.wear.data.user.repository.StoreRepositoryImpl
import com.mongs.wear.domain.collection.repository.CollectionRepository
import com.mongs.wear.domain.feedback.repository.FeedbackRepository
import com.mongs.wear.domain.player.repository.PlayerRepository
import com.mongs.wear.domain.store.repository.StoreRepository
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

    @Binds
    @Singleton
    abstract fun bindStoreRepository(repository: StoreRepositoryImpl) : StoreRepository
}