package com.paymong.wear.domain.viewModel.initLanding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.viewModel.code.InitLandingCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitLandingViewModel @Inject constructor(
) : ViewModel() {
    val processCode: LiveData<InitLandingCode> get() = _processCode
    private val _processCode = MutableLiveData(InitLandingCode.STAND_BY)

    private fun paymongLogin(userName: String?, email: String?) {
        viewModelScope.launch {
            var isSuccess = true

            // 계정 데이터 유효성 체크
            if (userName.isNullOrBlank()) {
                isSuccess = false
            } else if (email.isNullOrBlank()) {
                isSuccess = false
            }
            // Todo : 페이몽 로그인 로직
            Log.d("test", "paymongLogin >> userName : $userName, email : $email")
            delay(1000)

            if (isSuccess) {
                _processCode.postValue(InitLandingCode.SIGN_IN_SUCCESS)
                delay(1000)
                _processCode.postValue(InitLandingCode.END)
            } else {
                _processCode.postValue(InitLandingCode.SIGN_IN_INIT)
            }
        }
    }

    fun googleSignInCheck() {
        _processCode.postValue(InitLandingCode.SIGN_IN_CHECK)
    }
    fun googleSignInInit() {
        _processCode.postValue(InitLandingCode.SIGN_IN_INIT)
    }
    fun googleSignInAlready(userName: String, email: String) {
        paymongLogin(userName = userName, email = email)
    }
    fun googleSignInProcess() {
        _processCode.postValue(InitLandingCode.SIGN_IN_PROCESS_GOOGLE)
    }
    fun googleSignInSuccess(userName: String?, email: String?) {
        _processCode.postValue(InitLandingCode.SIGN_IN_PROCESS)
        paymongLogin(userName = userName, email = email)
    }
    fun googleSignInFail() {
        _processCode.postValue(InitLandingCode.SIGN_IN_INIT)
    }
}
