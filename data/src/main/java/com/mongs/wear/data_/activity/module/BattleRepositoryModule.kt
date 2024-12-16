package com.mongs.wear.data_.activity.module

import com.mongs.wear.data_.activity.repository.BattleRepositoryImpl
import com.mongs.wear.data_.auth.repository.AuthRepositoryImpl
import com.mongs.wear.domain.repositroy.AuthRepository
import com.mongs.wear.domain.repositroy.BattleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BattleRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBattleRepository(repository: BattleRepositoryImpl) : BattleRepository
}