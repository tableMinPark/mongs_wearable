package com.paymong.wear.domain.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.domain.model.AppInfoModel

interface AppInfoRepository {
    fun initSetAppInfo()
    fun getAppInfo() : LiveData<AppInfoModel>
}