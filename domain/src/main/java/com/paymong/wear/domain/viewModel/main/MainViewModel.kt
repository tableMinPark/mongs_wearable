package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.MongRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val mongRepository: MongRepository
) : ViewModel() {
    var mapCode: LiveData<String> = MutableLiveData(DefaultValue.mapCode)
    var mongCode: LiveData<String> = MutableLiveData(DefaultValue.mongCode)
    var stateCode: LiveData<String> = MutableLiveData(DefaultValue.stateCode)
    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("MainViewModel", "MainViewModel - init!")
            val mongModel = mongRepository.getMong()
            mongCode = mongModel.map { it.mongCode }
            stateCode = mongModel.map { it.stateCode }

            val appInfoModel = appInfoRepository.getAppInfo()
            mapCode = appInfoModel.map { it.mapCode }
        }
    }
}