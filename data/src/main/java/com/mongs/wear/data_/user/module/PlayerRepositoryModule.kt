package com.mongs.wear.data_.user.module

import com.mongs.wear.data_.user.repository.PlayerRepositoryImpl
import com.mongs.wear.domain.repositroy.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlayerRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlayerRepository(repository: PlayerRepositoryImpl) : PlayerRepository
}