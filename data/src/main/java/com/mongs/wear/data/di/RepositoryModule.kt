package com.mongs.wear.data.di

import com.mongs.wear.data.client.RealTimeRepository
import com.mongs.wear.data.repository.AuthRepositoryImpl
import com.mongs.wear.data.repository.CodeRepositoryImpl
import com.mongs.wear.data.repository.CollectionRepositoryImpl
import com.mongs.wear.data.repository.DeviceRepositoryImpl
import com.mongs.wear.data.repository.FeedbackRepositoryImpl
import com.mongs.wear.data.repository.ManagementRepositoryImpl
import com.mongs.wear.data.repository.MemberRepositoryImpl
import com.mongs.wear.data.repository.RealTimeRepositoryImpl
import com.mongs.wear.data.repository.SlotRepositoryImpl
import com.mongs.wear.domain.repositroy.AuthRepository
import com.mongs.wear.domain.repositroy.CodeRepository
import com.mongs.wear.domain.repositroy.CollectionRepository
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import com.mongs.wear.domain.repositroy.SlotRepository
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
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository
    @Binds
    @Singleton
    abstract fun bindCodeRepository(repository: CodeRepositoryImpl): CodeRepository
    @Binds
    @Singleton
    abstract fun bindCollectionRepository(repository: CollectionRepositoryImpl): CollectionRepository
    @Binds
    @Singleton
    abstract fun bindDeviceRepository(repository: DeviceRepositoryImpl): DeviceRepository
    @Binds
    @Singleton
    abstract fun bindFeedbackRepository(repository: FeedbackRepositoryImpl): FeedbackRepository
    @Binds
    @Singleton
    abstract fun bindManagementRepository(repository: ManagementRepositoryImpl): ManagementRepository
    @Binds
    @Singleton
    abstract fun bindMemberRepository(repository: MemberRepositoryImpl): MemberRepository
    @Binds
    @Singleton
    abstract fun bindRealTimeRepository(repository: RealTimeRepositoryImpl): RealTimeRepository
    @Binds
    @Singleton
    abstract fun bindSlotRepository(repository: SlotRepositoryImpl): SlotRepository
}