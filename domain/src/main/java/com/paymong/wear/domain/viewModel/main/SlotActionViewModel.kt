package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
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
    var stateCode: LiveData<String> = MutableLiveData(DefaultValue.stateCode)
    var poopCount: LiveData<Int> = MutableLiveData(DefaultValue.poopCount)

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("SlotActionViewModel", "SlotActionViewModel - init!")
//            val mongModel = mongRepository.getMong()
//            stateCode = mongModel.map { it.stateCode }
//            poopCount = mongModel.map { it.poopCount }
//        }
//    }

    fun sleep() {
        viewModelScope.launch(Dispatchers.IO) {
            val stateCode = mongRepository.getMongState()
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
            val nextMongState = mongRepository.getMongState()
            mongRepository.setMongState("CD009")
            delay(1000)
            mongRepository.setMongState(nextMongState)

            // TODO : stroke action (API)
        }
    }

}