package com.paymong.wear.ui.viewModel.initLanding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.error.AuthErrorCode
import com.paymong.wear.domain.exception.CommonException
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.processCode.InitLandingProcessCode
import com.paymong.wear.domain.repository.auth.AuthRepository
import com.paymong.wear.domain.repository.common.CodeRepository
import com.paymong.wear.domain.repository.common.DeviceRepository
import com.paymong.wear.domain.repository.common.MemberRepository
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import com.paymong.wear.domain.repository.notification.NotificationRepository
import com.paymong.wear.domain.repository.slot.SlotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class InitLandingViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val authRepository: AuthRepository,
    private val memberRepository: MemberRepository,
    private val codeRepository: CodeRepository,
    private val slotRepository: SlotRepository,
    private val notificationRepository: NotificationRepository,
    private val feedbackRepository: FeedbackRepository
) : ViewModel() {
    val processCode: LiveData<InitLandingProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(InitLandingProcessCode.GOOGLE_SIGN_IN_CHECK)

    fun resetProcessCode() {
        _processCode.postValue(InitLandingProcessCode.STAND_BY)
    }

    fun mongsLogin(email: String?, name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                    val deviceId = deviceRepository.getDeviceId()
                    val accountId = authRepository.login(deviceId = deviceId, email = email!!, name = name!!)
                    _processCode.postValue(InitLandingProcessCode.LOGIN_SUCCESS)

                    // 회원 정보 초기화
                    memberRepository.initializeMember()
                    // 코드 값 초기화
                    codeRepository.initializeCode()
                    // 슬롯 정보 초기화
                    slotRepository.initializeSlot()
                    // mqtt 연결
                    notificationRepository.initializeNotification(accountId = accountId)

                    _processCode.postValue(InitLandingProcessCode.NAV_MAIN)
            } catch (e: CommonException) {
                _processCode.postValue(InitLandingProcessCode.MUST_UPDATE_APP)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.AUTH.groupCode,
                    location = "InitLandingViewModel#mongsLogin",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(InitLandingProcessCode.MONGS_LOGIN_FAIL)
            }
        }
    }
    fun googleSignInCheck() {
        _processCode.postValue(InitLandingProcessCode.GOOGLE_SIGN_IN_CHECK)
    }
    fun googleSignIn() {
        _processCode.postValue(InitLandingProcessCode.LOGIN)
    }
    fun googleSignInFail() {
        viewModelScope.launch(Dispatchers.IO) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.AUTH.groupCode,
                location = "InitLandingViewModel#googleSignInFail",
                message = AuthErrorCode.LOGIN_FAIL.message(),
            )
            _processCode.postValue(InitLandingProcessCode.GOOGLE_SIGN_IN_FAIL)
        }
    }
}