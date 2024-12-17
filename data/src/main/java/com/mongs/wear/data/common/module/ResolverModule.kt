package com.mongs.wear.data.common.module

import com.mongs.wear.data.common.resolver.ObserveResolver
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
    fun bindPlayerObserveResolver() : ObserveResolver = ObserveResolver()
}