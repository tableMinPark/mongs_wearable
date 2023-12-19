package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.dto.DefaultCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotActionViewModel @Inject constructor(
) : ViewModel() {
    val stateCode: LiveData<String> get() = _stateCode
    private val _stateCode = MutableLiveData(DefaultCode.stateCode)
    val poopCount: LiveData<Int> get() = _poopCount
    private val _poopCount = MutableLiveData(DefaultCode.poopCount)

    fun sleep() {
        viewModelScope.launch {
            if (_stateCode.value == "CD002") {
                // TODO : wakeUp action (API)
                Log.d("SlotActionViewModel", "Call - sleep() : wakeUp")
//                _stateCode.postValue("CD002")
            } else {
                // TODO : sleep action (API)
                Log.d("SlotActionViewModel", "Call - sleep() : sleep")
//                _stateCode.postValue("CD002")
            }
        }
    }
    fun poop() {
        viewModelScope.launch {
            // TODO : poop clean action (API)
            Log.d("SlotActionViewModel", "Call - poop()")
        }
    }
    fun stroke() {
        viewModelScope.launch {
            Log.d("SlotActionViewModel", "Call - stroke()")
//            val tempStateCode = _stateCode.value
//            _stateCode.postValue("CD009")
//
//            // TODO : stroke action (API)
//
//            delay(1000)
//
//            // 진화, 졸업 등 상태 변화가 없는 경우, 이전의 상태로 변경
//            if (_stateCode.value == "CD009") {
//                _stateCode.postValue(tempStateCode)
//            }
        }
    }

}