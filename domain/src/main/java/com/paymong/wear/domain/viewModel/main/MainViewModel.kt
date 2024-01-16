package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.SlotRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.MainCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val slotRepository: SlotRepository
) : ViewModel() {
    val processCode: LiveData<MainCode> get() = _processCode
    private val _processCode = MutableLiveData(MainCode.LOAD)

    var mapCode: LiveData<String> = MutableLiveData(DefaultValue.mapCode)
    var mongCode: LiveData<String> = MutableLiveData(DefaultValue.mongCode)
    var stateCode: LiveData<String> = MutableLiveData(DefaultValue.stateCode)
    var shiftCode: LiveData<String> = MutableLiveData(DefaultValue.shiftCode)
    var poopCount: LiveData<Int> = MutableLiveData(DefaultValue.poopCount)

    var health: LiveData<Float> = MutableLiveData(DefaultValue.health)
    var satiety: LiveData<Float> = MutableLiveData(DefaultValue.satiety)
    var strength: LiveData<Float> = MutableLiveData(DefaultValue.strength)
    var sleep: LiveData<Float> = MutableLiveData(DefaultValue.sleep)


    private var networkFlag: LiveData<Boolean> = MutableLiveData(false)
    private var sound: LiveData<Float> = MutableLiveData(DefaultValue.sound)

    init {
        Log.d("MainViewModel", "MainViewModel - init!")
        networkFlag = appInfoRepository.getNetworkFlag()
        sound = appInfoRepository.getConfigureSound()
        mapCode = appInfoRepository.getAppInfoMapCode()

        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.async {
                val slotModel = slotRepository.getSlot()
                mongCode = slotModel.map { it.mongCode }
                stateCode = slotModel.map { it.stateCode }
                shiftCode = slotModel.map { it.shiftCode }
                poopCount = slotModel.map { it.poopCount }

                mongCode = slotModel.map { it.mongCode }
                health = slotModel.map { it.health }
                satiety = slotModel.map { it.satiety }
                strength = slotModel.map { it.strength }
                sleep = slotModel.map { it.sleep }
            }.await()

            delay(300)
            _processCode.postValue(MainCode.STAND_BY)
        }

        networkFlag.observeForever {
            Log.d("MainViewModel", "network flag : $it")
        }
    }
}