package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.FeedModel

interface FeedInfoRepository {
    fun initSetFeedInfo()
    fun getFoodList(): LiveData<List<FeedModel>>
    fun getSnackList(): LiveData<List<FeedModel>>
}