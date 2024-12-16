package com.mongs.wear.data_.manager.module

import com.mongs.wear.data_.manager.repository.ManagementRepositoryImpl
import com.mongs.wear.domain.repositroy.ManagementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagementRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindManagementRepository(repository: ManagementRepositoryImpl) : ManagementRepository
}