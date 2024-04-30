package com.paymong.wear.data.di

import android.content.Context
import com.paymong.wear.data.dataStore.configure.ConfigureDataStore
import com.paymong.wear.data.dataStore.device.DeviceDataStore
import com.paymong.wear.data.dataStore.member.MemberDataStore
import com.paymong.wear.data.dataStore.token.TokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun bindConfigureDataStore(@ApplicationContext context: Context) : ConfigureDataStore {
        return ConfigureDataStore(context)
    }
    @Provides
    @Singleton
    fun bindDeviceDataStore(@ApplicationContext context: Context) : DeviceDataStore {
        return DeviceDataStore(context)
    }
    @Provides
    @Singleton
    fun bindMemberDataStore(@ApplicationContext context: Context) : MemberDataStore {
        return MemberDataStore(context)
    }
    @Provides
    @Singleton
    fun bindTokenDataStore(@ApplicationContext context: Context) : TokenDataStore {
        return TokenDataStore(context)
    }
}