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
import com.paymong.wear.domain.viewModel.code.FeedSelectCode
import com.paymong.wear.domain.viewModel.code.MainCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val mongRepository: MongRepository
) : ViewModel() {
    val processCode: LiveData<MainCode> get() = _processCode
    private val _processCode = MutableLiveData(MainCode.LOAD)

    var mapCode: LiveData<String> = MutableLiveData(DefaultValue.mapCode)
    var mongCode: LiveData<String> = MutableLiveData(DefaultValue.mongCode)
    var stateCode: LiveData<String> = MutableLiveData(DefaultValue.stateCode)
    var poopCount: LiveData<Int> = MutableLiveData(DefaultValue.poopCount)

    var health: LiveData<Float> = MutableLiveData(DefaultValue.health)
    var satiety: LiveData<Float> = MutableLiveData(DefaultValue.satiety)
    var strength: LiveData<Float> = MutableLiveData(DefaultValue.strength)
    var sleep: LiveData<Float> = MutableLiveData(DefaultValue.sleep)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("MainViewModel", "MainViewModel - init!")
            val mongModel = mongRepository.getMong()
            mongCode = mongModel.map { it.mongCode }
            stateCode = mongModel.map { it.stateCode }
            poopCount = mongModel.map { it.poopCount }

            mongCode = mongModel.map { it.mongCode }
            health = mongModel.map { it.health }
            satiety = mongModel.map { it.satiety }
            strength = mongModel.map { it.strength }
            sleep = mongModel.map { it.sleep }

            val appInfoModel = appInfoRepository.getAppInfo()
            mapCode = appInfoModel.map { it.mapCode }

            delay(300)
            _processCode.postValue(MainCode.STAND_BY)
        }
    }
}