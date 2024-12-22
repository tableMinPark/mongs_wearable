package com.mongs.wear.data.common.module

import com.mongs.wear.data.common.datastore.AppDataStore
import com.mongs.wear.data.common.resolver.ObserveResolver
import com.mongs.wear.data.common.room.RoomDB
import com.mongs.wear.data.user.datastore.PlayerDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResolverModule {

    @Provides
    @Singleton
    fun bindPlayerObserveResolver(
        roomDB: RoomDB,
        appDataStore: AppDataStore,
        playerDataStore: PlayerDataStore,
    ) : ObserveResolver = ObserveResolver(
        roomDB = roomDB,
        appDataStore = appDataStore,
        playerDataStore = playerDataStore,
    )
}