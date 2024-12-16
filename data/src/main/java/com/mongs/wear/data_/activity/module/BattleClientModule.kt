package com.mongs.wear.data_.activity.module

import com.mongs.wear.data_.activity.client.BattleMqttClientImpl
import com.mongs.wear.domain.client.BattleMqttClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BattleClientModule {

    @Binds
    @Singleton
    abstract fun bindBattleClient(client: BattleMqttClientImpl) : BattleMqttClient
}