package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.AppInfoModel
import com.paymong.wear.domain.model.FeedModel

interface AppInfoRepository {
    fun initSetAppInfo()
    fun initSetAppConfigureInfo()
    fun initSetCharacterInfo()
    fun initSetFeedInfo()
    fun getAppInfo() : LiveData<AppInfoModel>
    fun getFoodList(): LiveData<List<FeedModel>>
    fun getSnackList(): LiveData<List<FeedModel>>

}