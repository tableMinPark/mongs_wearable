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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val mongRepository: MongRepository
) : ViewModel() {
    var mapCode: LiveData<String> = MutableLiveData(DefaultValue.mapCode)
    var mongCode: LiveData<String> get() = _mongModel.map { it.mongCode }
    var stateCode: LiveData<String> get() = _mongModel.map { it.stateCode }

    private val _mongModel = MediatorLiveData<MongModel>()
    private val liveDataObserver = Observer<LiveData<MongModel>> { innerLiveData ->
        innerLiveData.observeForever { mongModel ->
            Log.d("MainViewModel", mongModel.toString())
            _mongModel.value = mongModel
        }
    }

    init {
        mongCode = MutableLiveData(DefaultValue.mongCode)
        stateCode = MutableLiveData(DefaultValue.stateCode)
        _mongModel.addSource(mongRepository.getMong(), liveDataObserver)

        viewModelScope.launch(Dispatchers.IO) {
            val appInfoModel = appInfoRepository.getAppInfo()
            mapCode = appInfoModel.map { it.mapCode }
        }
    }
    override fun onCleared() {
        super.onCleared()
        mongRepository.getMong().removeObserver(liveDataObserver)
        Log.d("MainViewModel", "removeObserver")
    }
}