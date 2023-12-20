package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.dto.DefaultCode
import com.paymong.wear.domain.repository.MongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    val mongCode: LiveData<String> get() = _mongCode
    private val _mongCode = MutableLiveData(DefaultCode.mongCode)
    val stateCode: LiveData<String> get() = _stateCode
    private val _stateCode = MutableLiveData(DefaultCode.stateCode)
    val poopCount: LiveData<Int> get() = _poopCount
    private val _poopCount = MutableLiveData(DefaultCode.poopCount)

    private val _nextMongCode = MutableLiveData(DefaultCode.nextMongCode)
    private val _nextStateCode = MutableLiveData(DefaultCode.nextStateCode)

    init {
        viewModelScope.launch {
//            _mongCode.postValue("CH100")
//            _stateCode.postValue("CD000")
//            _poopCount.postValue(4)
//            _mongCode.postValue("CH000")
//            _stateCode.postValue("CD000")
//            _poopCount.postValue(4)
//            delay(1000)
//            _nextStateCode.postValue(_stateCode.value)
//            _stateCode.postValue("CD007")
//            _nextMongCode.postValue("CH100")
//            delay(5000)
//            _nextStateCode.postValue(_stateCode.value)
//            _stateCode.postValue("CD007")
//            _nextMongCode.postValue("CH200")
//            delay(5000)
//            _nextStateCode.postValue(_stateCode.value)
//            _stateCode.postValue("CD007")
//            _nextMongCode.postValue("CH300")
//            delay(5000)
//            _stateCode.postValue("CD006")
        }
    }


//    var stateCode by mutableStateOf("CD444")
//    var poopCount by mutableStateOf(0)
//    var mongCode by mutableStateOf("CH444")
//
//    private var nextMongCode by mutableStateOf("")
//    private var nextStateCode by mutableStateOf("")
//
//    init {
//        mongRepository.getMongCondition().observeForever { mongCondition ->
//            Log.d("test", mongCondition.toString())
//        }
//
//        mongRepository.setMongCondition(MongCondition(0.5f, 0.5f,0.5f,0.5f))
////        mongRepository.getStateCode().observeForever { stateCode -> this.stateCode = stateCode }
////        mongRepository.getPoopCount().observeForever { poopCount -> this.poopCount = poopCount }
////        mongRepository.getMongCode().observeForever { mongCode -> this.mongCode = mongCode }
//    }

    fun generateMong() {
        Log.d("SlotViewModel", "Call - generateMong()")
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
        _mongCode.postValue("CH444")
        _stateCode.postValue("CD444")
        _poopCount.postValue(0)
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