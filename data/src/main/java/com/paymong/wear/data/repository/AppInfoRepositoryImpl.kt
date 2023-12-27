package com.paymong.wear.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.wear.data.entity.AppInfo
import com.paymong.wear.domain.model.AppInfoModel
import com.paymong.wear.domain.repository.AppInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor(
) : AppInfoRepository {
    private val appInfoModel: LiveData<AppInfoModel> get() = _appInfoModel
    private val _appInfoModel = MutableLiveData<AppInfoModel>()

    override fun initSetAppInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO : 앱 인포 (API)
            val mapCode = "MP000"

            _appInfoModel.postValue(AppInfoModel(mapCode = mapCode))
            Log.d("AppInfoRepository", "Call - initSetAppInfo()")
        }
    }

    override fun getAppInfo() : LiveData<AppInfoModel> {
        return this@AppInfoRepositoryImpl.appInfoModel
    }
}