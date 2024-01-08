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
        // 진화 중 상태로 변경
//        mongRepository.setStateCode("CD010")
//        _stateCode.postValue("CD010")
    }

    fun evolutionEnd() {
        Log.d("SlotViewModel", "Call - evolutionEnd()")
//        _mongCode.postValue(_nextMongCode.value)
//        _nextMongCode.postValue("CH444")
//        _stateCode.postValue(_nextStateCode.value)
//        _nextStateCode.postValue("CD444")
    }

    fun graduation() {
        Log.d("SlotViewModel", "Call - graduation()")
//        _mongCode.postValue("CH444")
//        _stateCode.postValue("CD444")
//        _poopCount.postValue(0)
    }

    // TODO : 카프카 클라이언트가 Repository의 값을 변경하면 옵저버가 감지하기 때문에 필요없음
//    private fun updateForEvolution(nextMongCode: String, stateCode: String) {
//        this.nextMongCode = nextMongCode
//        this.nextStateCode = this.stateCode
//        this.stateCode = stateCode
//    }

    // TODO : 카프카 클라이언트가 Repository의 값을 변경하면 옵저버가 감지하기 때문에 필요없음
//    private fun updateForGraduation() {
//
//        this.stateCode = "CD006"
//    }
}