package com.mongs.wear.data.common.module

import com.mongs.wear.data.common.repository.AppRepositoryImpl
import com.mongs.wear.domain.repositroy.AppRepository
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
    abstract fun bindAppRepository(repository: AppRepositoryImpl): AppRepository
}