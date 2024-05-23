package com.paymong.wear.data.di

import com.paymong.wear.data.client.MqttClientImpl
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
import com.paymong.wear.domain.client.MqttClient
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
abstract class ClientModule {
    @Binds
    @Singleton
    abstract fun bindSlotRepository(client: MqttClientImpl): MqttClient
}