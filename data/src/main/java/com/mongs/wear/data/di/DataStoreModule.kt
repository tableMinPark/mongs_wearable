package com.mongs.wear.data.di

import android.content.Context
import com.mongs.wear.data.dataStore.BattleDataStore
import com.mongs.wear.data.dataStore.DeviceDataStore
import com.mongs.wear.data.dataStore.MemberDataStore
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
    @Provides
    @Singleton
    fun bindBattleDataStore(@ApplicationContext context: Context) : BattleDataStore {
        return BattleDataStore(context)
    }
}