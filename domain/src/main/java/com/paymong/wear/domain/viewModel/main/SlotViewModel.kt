package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.SlotRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.SlotCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotViewModel @Inject constructor(
    private val slotRepository: SlotRepository
) : ViewModel() {
    val processCode: LiveData<SlotCode> get() = _processCode
    private val _processCode = MutableLiveData(SlotCode.STAND_BY)

    private var nextMongCode: LiveData<String> = MutableLiveData(DefaultValue.nextMongCode)
    private var nextStateCode: LiveData<String> = MutableLiveData(DefaultValue.nextStateCode)

    init {
        Log.d("SlotViewModel", "SlotViewModel - init!")
        viewModelScope.launch(Dispatchers.IO) {
            val mongModel = slotRepository.getSlot()
            nextMongCode = mongModel.map { it.nextMongCode }
            nextStateCode = mongModel.map { it.nextStateCode }
        }
    }

    fun generateMong() {
        Log.d("SlotViewModel", "Call - generateMong()")
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotCode.GENERATE_MONG)
            viewModelScope.async(Dispatchers.IO) { slotRepository.generateSlot() }.await()
            _processCode.postValue(SlotCode.NAVIGATE)
        }
    }

    fun evolutionStart() {
        Log.d("SlotViewModel", "Call - evolutionStart()")
        viewModelScope.launch(Dispatchers.IO) {
            // 진화 중 상태로 변경
            slotRepository.setSlotShift("SH004")
        }
    }

    fun evolutionEnd() {
        Log.d("SlotViewModel", "Call - evolutionEnd()")
        viewModelScope.launch(Dispatchers.IO) {
            // 변화 중지 상태로 변경
            slotRepository.setSlotShift("SH444")
            slotRepository.setSlotMongCodeToNextMongCode()
        }
    }

    fun graduation() {
        Log.d("SlotViewModel", "Call - graduation()")
    }
}