package com.paymong.wear.domain.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.dto.DefaultCode
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.MongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mongRepository: MongRepository,
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {
    val isSlotEmpty: LiveData<Boolean> get() = _isSlotEmpty
    private val _isSlotEmpty = MutableLiveData(true)
    val mapCode: LiveData<String> get() = _mapCode
    private val _mapCode = MutableLiveData(DefaultCode.mapCode)
    val mongCode: LiveData<String> get() = _mongCode
    private val _mongCode = MutableLiveData(DefaultCode.mongCode)
    val stateCode: LiveData<String> get() = _stateCode
    private val _stateCode = MutableLiveData(DefaultCode.stateCode)


    init {

//        appInfoRepository.getMapCode().observeForever { mapCode -> this.mapCode = mapCode }
//        mongRepository.getMongCode().observeForever { mongCode -> this.mongCode = mongCode }
//        mongRepository.getStateCode().observeForever { stateCode -> this.stateCode = stateCode }


//        sharedRepository.getMapCode().observeForever { mapCode -> this.mapCode = mapCode }
//        mongInfoSharedRepository.getMongCode().observeForever { mongCode -> this.mongCode = mongCode }
//        mongStateSharedRepository.getStateCode().observeForever { stateCode -> this.stateCode = stateCode }
//
//        // TODO : 단위 테스트 (몽 상태 변화에 따른 화면 전환)
//        mongInfoSharedRepository.getNextMongCode().observeForever { nextMongCode -> this.nextMongCode = nextMongCode }
//        mongStateSharedRepository.getNextStateCode().observeForever { nextStateCode -> this.nextStateCode = nextStateCode }
//        viewModelScope.launch {
//            // 먹는중 -> 행복 -> 진화 대기 -> 진화 중 -> 졸업 ->  죽음
//            for (stateCode in arrayOf("CD008", "CD009", "CD007", "CD010", "CD006", "CD005")) {
//                when(stateCode) {
//                    "CD008" -> {}
//                    "CD009" -> {}
//                    "CD007" -> {
//                        mongStateSharedRepository.setNextStateCode("CD009")
//                        mongInfoSharedRepository.setNextMongCode("CH200")
//                    }
//                    "CD010" -> {}
//                    "CD006" -> {}
//                    "CD005" -> {
//                        mongConditionSharedRepository.setHealth(0.0f, 1L)
//                        mongConditionSharedRepository.setSatiety(0.0f, 1L)
//                        mongConditionSharedRepository.setStrength(0.0f, 1L)
//                        mongConditionSharedRepository.setSleep(0.0f, 1L)
//                    }
//                    else -> {}
//                }
//                mongStateSharedRepository.setStateCode(stateCode)
//                delay(6000)
//            }
//        }
    }
}