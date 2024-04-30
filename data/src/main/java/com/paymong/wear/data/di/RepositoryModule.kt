package com.paymong.wear.data.di

import com.paymong.wear.data.repository.auth.AuthRepositoryImpl
import com.paymong.wear.data.repository.collection.CollectionRepositoryImpl
import com.paymong.wear.data.repository.common.CodeRepositoryImpl
import com.paymong.wear.data.repository.common.ConfigureRepositoryImpl
import com.paymong.wear.data.repository.common.DeviceRepositoryImpl
import com.paymong.wear.data.repository.common.MemberRepositoryImpl
import com.paymong.wear.data.repository.feedback.FeedbackRepositoryImpl
import com.paymong.wear.data.repository.notification.NotificationRepositoryImpl
import com.paymong.wear.data.repository.notification.callback.CallbackRepository
import com.paymong.wear.data.repository.notification.callback.CallbackRepositoryImpl
import com.paymong.wear.data.repository.slot.SlotRepositoryImpl
import com.paymong.wear.domain.repository.auth.AuthRepository
import com.paymong.wear.domain.repository.collection.CollectionRepository
import com.paymong.wear.domain.repository.common.CodeRepository
import com.paymong.wear.domain.repository.common.ConfigureRepository
import com.paymong.wear.domain.repository.common.DeviceRepository
import com.paymong.wear.domain.repository.common.MemberRepository
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import com.paymong.wear.domain.repository.notification.NotificationRepository
import com.paymong.wear.domain.repository.slot.SlotRepository
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
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl) : AuthRepository
    @Binds
    @Singleton
    abstract fun bindCollectionRepository(repository: CollectionRepositoryImpl) : CollectionRepository
    @Binds
    @Singleton
    abstract fun bindCodeRepository(repository: CodeRepositoryImpl) : CodeRepository
    @Binds
    @Singleton
    abstract fun bindConfigureRepository(repository: ConfigureRepositoryImpl) : ConfigureRepository
    @Binds
    @Singleton
    abstract fun bindDeviceRepository(repository: DeviceRepositoryImpl) : DeviceRepository
    @Binds
    @Singleton
    abstract fun bindMemberRepository(repository: MemberRepositoryImpl) : MemberRepository
    @Binds
    @Singleton
    abstract fun bindFeedbackRepository(repository: FeedbackRepositoryImpl) : FeedbackRepository
    @Binds
    @Singleton
    abstract fun bindNotificationRepository(repository: NotificationRepositoryImpl) : NotificationRepository
    @Binds
    @Singleton
    abstract fun bindSlotRepository(repository: SlotRepositoryImpl) : SlotRepository
    @Binds
    @Singleton
    abstract fun bindCallbackRepository(repository: CallbackRepositoryImpl) : CallbackRepository
}