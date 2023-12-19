package com.paymong.wear.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.wear.domain.repository.AppInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor() : AppInfoRepository {
    private val _mapCode: MutableLiveData<String> = MutableLiveData<String>()
    private val mapCode: LiveData<String> get() = _mapCode

    init {
        initAppInfo()
    }

    override fun initAppInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO : API 호출해서 mapCode 받아옴
            modifyMapCode("MP000")
        }
    }

    override fun modifyMapCode(mapCode: String) {
        this._mapCode.postValue(mapCode)
    }

    override fun findMapCode(): LiveData<String> {
        return this.mapCode
    }
}