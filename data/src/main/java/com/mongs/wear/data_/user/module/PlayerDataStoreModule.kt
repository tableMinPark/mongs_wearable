package com.mongs.wear.data_.user.module

import android.content.Context
import com.mongs.wear.data_.user.datastore.PlayerDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerDataStoreModule {

    @Provides
    @Singleton
    fun bindPlayerDataStore(@ApplicationContext context: Context) : PlayerDataStore = PlayerDataStore(context)
}