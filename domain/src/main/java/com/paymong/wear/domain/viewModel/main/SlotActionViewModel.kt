package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.MongRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotActionViewModel @Inject constructor(
    val mongRepository: MongRepository
) : ViewModel() {
    var stateCode: LiveData<String> get() = _mongModel.map { it.stateCode }
    var poopCount: LiveData<Int> get() = _mongModel.map { it.poopCount }

    private val _mongModel = MediatorLiveData<MongModel>()
    private val liveDataObserver = Observer<LiveData<MongModel>> { innerLiveData ->
        innerLiveData.observeForever { mongModel ->
            _mongModel.value = mongModel
        }
    }
    init {
        Log.d("SlotActionViewModel", "init")
        stateCode = MutableLiveData(DefaultValue.stateCode)
        poopCount = MutableLiveData(DefaultValue.poopCount)
        _mongModel.addSource(mongRepository.getMong(), liveDataObserver)
    }

    override fun onCleared() {
        super.onCleared()
        mongRepository.getMong().removeObserver(liveDataObserver)
        Log.d("SlotActionViewModel", "removeObserver")
    }

    fun sleep() {
        viewModelScope.launch(Dispatchers.IO) {
            val stateCode = mongRepository.findMongState()
            if (stateCode == "CD002") {
                // TODO : wakeUp action (API)
                Log.d("SlotActionViewModel", "Call - sleep() : wakeUp")
                mongRepository.setMongWakeUp()
            } else {
                // TODO : sleep action (API)
                Log.d("SlotActionViewModel", "Call - sleep() : sleep")
                mongRepository.setMongSleep()
            }
        }
    }
    fun poop() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO : poop clean action (API)
            Log.d("SlotActionViewModel", "Call - poop()")
            mongRepository.setPoopCount(0)
        }
    }
    fun stroke() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("SlotActionViewModel", "Call - stroke()")
            val nextMongState = mongRepository.findMongState()
            mongRepository.setMongState("CD009")
            delay(1000)
            mongRepository.setMongState(nextMongState)

            // TODO : stroke action (API)
        }
    }

}