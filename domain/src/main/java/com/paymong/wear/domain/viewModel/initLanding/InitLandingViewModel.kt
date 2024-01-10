package com.paymong.wear.domain.viewModel.initLanding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.MqttRepository
import com.paymong.wear.domain.repository.SlotRepository
import com.paymong.wear.domain.viewModel.code.InitLandingCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitLandingViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val slotRepository: SlotRepository,
    private val mqttRepository: MqttRepository
) : ViewModel() {
    val processCode: LiveData<InitLandingCode> get() = _processCode
    private val _processCode = MutableLiveData(InitLandingCode.STAND_BY)

    private fun paymongLogin(userName: String?, email: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            var isSuccess = true

            // 계정 데이터 유효성 체크
            if (userName.isNullOrBlank()) {
                isSuccess = false
            } else if (email.isNullOrBlank()) {
                isSuccess = false
            }
            // Todo : 페이몽 로그인 로직
            Log.d("InitLandingViewModel", "call paymongLogin: [userName : $userName, email : $email]")

            if (isSuccess) {
                _processCode.postValue(InitLandingCode.SIGN_IN_SUCCESS)

                viewModelScope.async(Dispatchers.IO) {
                    // 구독 걸고
                    email?.let { mqttRepository.connectAfterLogin(email) }
                }.await()

                viewModelScope.async(Dispatchers.IO) {
                    appInfoRepository.initSetAppInfo()
                    appInfoRepository.initSetConfigureInfo()
                    appInfoRepository.initSetMongInfo()
                    appInfoRepository.initSetFeedInfo()
                    slotRepository.initSlotInfo()
                }.await()
                _processCode.postValue(InitLandingCode.NAVIGATE)
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
