package com.mongs.wear.data_.manager.module

import com.mongs.wear.data_.manager.repository.SlotRepositoryImpl
import com.mongs.wear.domain.repositroy.SlotRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SlotRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSlotRepository(repository: SlotRepositoryImpl) : SlotRepository
}