package com.mongs.wear.data.user.module

import android.content.Context
import com.mongs.wear.data.user.datastore.PlayerDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataStoreModule {

    @Provides
    @Singleton
    fun bindPlayerDataStore(@ApplicationContext context: Context) : PlayerDataStore = PlayerDataStore(context)
}