package com.paymong.wear.domain.viewModel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paymong.wear.domain.dto.DefaultCode
import com.paymong.wear.domain.repository.MongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InteractionViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    val stateCode: LiveData<String> get() = _stateCode
    private val _stateCode = MutableLiveData(DefaultCode.stateCode)

    init {
//        mongRepository.getStateCode().observeForever { stateCode -> this.stateCode = stateCode }
    }

//    fun sleep() {
//        // 수면 불가능 상태
//        if (stateCode in arrayOf("CD005", "CD006", "CD007", "CD008", "CD010", "CD444")) {
//            this.processCode = InteractionCode.ERROR
//        } else {
//            if (stateCode == "CD002") {
//                // 수면 종료
//                // TODO : 캐릭터 상태 최신화 (API 호출 필요)
////                mongStateSharedRepository.setStateCode("CD000")   // 최신화 된 상태 코드가 삽입
//            } else {
//                // 수면 돌입
////                mongStateSharedRepository.setStateCode("CD002")
//            }
//            this.processCode = InteractionCode.SLEEP
//        }
//    }

//    fun poop() {
//        // 배변 처리 활동 불가능 상태
//        if (stateCode in arrayOf("CD005", "CD006", "CD008", "CD010", "CD444")) {
//            this.processCode = InteractionCode.ERROR
//        } else {
//            // TODO : 배변 처리 활동 API 호출
////            mongStateSharedRepository.setPoopCount(0)
//            this.processCode = InteractionCode.POOP
//        }
//    }
}