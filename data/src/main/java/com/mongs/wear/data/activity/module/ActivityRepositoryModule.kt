package com.mongs.wear.data.activity.module

import com.mongs.wear.data.activity.repository.BattleRepositoryImpl
import com.mongs.wear.domain.battle.repository.BattleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBattleRepository(repository: BattleRepositoryImpl) : BattleRepository
}