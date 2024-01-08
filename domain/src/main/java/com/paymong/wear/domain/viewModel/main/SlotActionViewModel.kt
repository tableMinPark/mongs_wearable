package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.SlotRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotActionViewModel @Inject constructor(
    private val slotRepository: SlotRepository
) : ViewModel() {
    fun sleep() {
        viewModelScope.launch(Dispatchers.IO) {
            val stateCode = slotRepository.getSlotMongState()
            if (stateCode == "CD002") {
                // TODO : wakeUp action (API)
                Log.d("SlotActionViewModel", "Call - sleep() : wakeUp")
                slotRepository.setSlotMongWakeUp()
            } else {
                // TODO : sleep action (API)
                Log.d("SlotActionViewModel", "Call - sleep() : sleep")
                slotRepository.setSlotMongSleep()
            }
        }
    }
    fun poop() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO : poop clean action (API)
            Log.d("SlotActionViewModel", "Call - poop()")
            slotRepository.setSlotMongPoopCount(0)
        }
    }
    fun stroke() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("SlotActionViewModel", "Call - stroke()")
            val nextMongState = slotRepository.getSlotMongState()
            slotRepository.setSlotMongState("CD009")
            delay(1000)
            slotRepository.setSlotMongState(nextMongState)

            // TODO : stroke action (API)
        }
    }
}