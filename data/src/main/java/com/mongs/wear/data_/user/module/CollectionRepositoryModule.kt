package com.mongs.wear.data_.user.module

import com.mongs.wear.data_.user.repository.CollectionRepositoryImpl
import com.mongs.wear.domain.repositroy.CollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCollectionRepository(repository: CollectionRepositoryImpl) : CollectionRepository
}