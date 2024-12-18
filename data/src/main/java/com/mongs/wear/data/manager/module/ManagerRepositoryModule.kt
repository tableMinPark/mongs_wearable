package com.mongs.wear.data.manager.module

import com.mongs.wear.data.manager.repository.ManagementRepositoryImpl
import com.mongs.wear.data.manager.repository.SlotRepositoryImpl
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.repository.SlotRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindManagementRepository(repository: ManagementRepositoryImpl) : ManagementRepository

    @Binds
    @Singleton
    abstract fun bindSlotRepository(repository: SlotRepositoryImpl) : SlotRepository
}