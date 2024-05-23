package com.paymong.wear.data.di

import com.paymong.wear.data.client.RealTimeRepository
import com.paymong.wear.data.repository.AuthRepositoryImpl
import com.paymong.wear.data.repository.CodeRepositoryImpl
import com.paymong.wear.data.repository.CollectionRepositoryImpl
import com.paymong.wear.data.repository.DeviceRepositoryImpl
import com.paymong.wear.data.repository.FeedbackRepositoryImpl
import com.paymong.wear.data.repository.ManagementRepositoryImpl
import com.paymong.wear.data.repository.MemberRepositoryImpl
import com.paymong.wear.data.repository.RealTimeRepositoryImpl
import com.paymong.wear.data.repository.SlotRepositoryImpl
import com.paymong.wear.domain.repositroy.AuthRepository
import com.paymong.wear.domain.repositroy.CodeRepository
import com.paymong.wear.domain.repositroy.CollectionRepository
import com.paymong.wear.domain.repositroy.DeviceRepository
import com.paymong.wear.domain.repositroy.FeedbackRepository
import com.paymong.wear.domain.repositroy.ManagementRepository
import com.paymong.wear.domain.repositroy.MemberRepository
import com.paymong.wear.domain.repositroy.SlotRepository
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