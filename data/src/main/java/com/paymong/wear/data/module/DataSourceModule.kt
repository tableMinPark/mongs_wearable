package com.paymong.wear.data.module

import android.content.Context
import com.paymong.wear.data.dataStore.DataSource
import com.paymong.wear.data.repository.AppInfoRepositoryImpl
import com.paymong.wear.data.repository.AuthRepositoryImpl
import com.paymong.wear.data.repository.SlotRepositoryImpl
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.AuthRepository
import com.paymong.wear.domain.repository.SlotRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun bindDataSource(@ApplicationContext context: Context) : DataSource {
        return DataSource(context)
    }
}