package com.paymong.wear.domain.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainLandingViewModel @Inject constructor(
) : ViewModel() {
    var processCode by mutableStateOf(MainLandingCode.START)

    fun login() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(500)
            }
            processCode = MainLandingCode.SUCCESS
        }
    }
}

enum class MainLandingCode(
    val message: String
) {
    START("로그인 시도 준비..."),
    LOADING("로그인 시도 중..."),
    SUCCESS("로그인 성공"),
    FAIL("로그인을 할 수 없습니다."),
    ERROR("로그인에 실패했습니다.\n네트워크 환경을 확인해주세요."),
    END("로그인 시도 끝...")
}
