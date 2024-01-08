package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.AppInfoModel
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.model.FeedModel

interface AppInfoRepository {
    suspend fun initSetAppInfo()
    suspend fun initSetConfigureInfo()
    suspend fun initSetMongInfo()
    suspend fun initSetFeedInfo()

    suspend fun getMongInfo(code: String): MongModel
    suspend fun getFoodList(): LiveData<List<FeedModel>>
    suspend fun getSnackList(): LiveData<List<FeedModel>>

    fun getConfigureSound(): LiveData<Float>
    suspend fun setConfigureSound(sound: Float)
    fun getAppInfoMapCode(): LiveData<String>
    fun getAppInfoMaxSlot(): LiveData<Int>
    fun getAppInfoPayPoint(): LiveData<Int>
}