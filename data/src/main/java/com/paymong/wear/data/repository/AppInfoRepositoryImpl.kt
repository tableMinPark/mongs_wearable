package com.paymong.wear.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.paymong.wear.domain.dto.model.AppInfoModel
import com.paymong.wear.domain.repository.AppInfoRepository
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor(
) : AppInfoRepository {
    private val appInfoModel: LiveData<AppInfoModel> get() = _appInfoModel
    private val _appInfoModel = MutableLiveData<AppInfoModel>()

    override fun initSetAppInfo() {
        // TODO : 앱 인포 (API)
        val mapCode = "MP000"

        _appInfoModel.postValue(AppInfoModel(mapCode = mapCode))
    }

    override fun getAppInfo() : LiveData<AppInfoModel> {
        return this@AppInfoRepositoryImpl.appInfoModel
    }
}