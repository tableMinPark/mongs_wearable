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
import com.paymong.wear.domain.dto.DefaultCode
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.MongRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    var mongCode: LiveData<String> get() = _mongModel.map { it.mongCode }
    var stateCode: LiveData<String> get() = _mongModel.map { it.stateCode }
    var poopCount: LiveData<Int> get() = _mongModel.map { it.poopCount }
    private var nextMongCode: LiveData<String> get() = _mongModel.map { it.nextMongCode }
    private var nextStateCode: LiveData<String> get() = _mongModel.map { it.nextStateCode }

    private val _mongModel = MediatorLiveData<MongModel>()
    private val liveDataObserver = Observer<LiveData<MongModel>> { innerLiveData ->
        innerLiveData.observeForever { mongModel ->
            Log.d("SlotViewModel", mongModel.toString())
            _mongModel.value = mongModel
        }
    }
    init {
        mongCode = MutableLiveData(DefaultValue.mongCode)
        stateCode = MutableLiveData(DefaultValue.stateCode)
        poopCount = MutableLiveData(DefaultValue.poopCount)
        nextMongCode = MutableLiveData(DefaultValue.nextMongCode)
        nextStateCode = MutableLiveData(DefaultValue.nextStateCode)
        _mongModel.addSource(mongRepository.getMong(), liveDataObserver)
    }
    override fun onCleared() {
        super.onCleared()
        mongRepository.getMong().removeObserver(liveDataObserver)
        Log.d("SlotViewModel", "removeObserver")
    }

    fun generateMong() {
        Log.d("SlotViewModel", "Call - generateMong()")
        viewModelScope.launch(Dispatchers.IO) {
            mongRepository.generateMong()
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