package com.mongs.wear.data.auth.module

import android.content.Context
import com.mongs.wear.data.auth.dataStore.TokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenDataStoreModule {

    @Provides
    @Singleton
    fun bindTokenDataStore(@ApplicationContext context: Context): TokenDataStore = TokenDataStore(context)
}