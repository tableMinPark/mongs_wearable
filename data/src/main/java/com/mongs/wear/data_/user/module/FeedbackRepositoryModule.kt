package com.mongs.wear.data_.user.module

import com.mongs.wear.data_.user.repository.FeedbackRepositoryImpl
import com.mongs.wear.domain.repositroy.FeedbackRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedbackRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFeedbackRepository(repository: FeedbackRepositoryImpl) : FeedbackRepository
}