package com.paymong.wear.data.di

import android.content.Context
import com.paymong.wear.data.dataStore.DeviceDataStore
import com.paymong.wear.data.dataStore.MemberDataStore
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
    fun bindDeviceDataStore(@ApplicationContext context: Context) : DeviceDataStore {
        return DeviceDataStore(context)
    }
    @Provides
    @Singleton
    fun bindMemberDataStore(@ApplicationContext context: Context) : MemberDataStore {
        return MemberDataStore(context)
    }
}