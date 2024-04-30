package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.error.AuthErrorCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.processCode.InitLandingProcessCode
import com.paymong.wear.domain.processCode.MainConfigureProcessCode
import com.paymong.wear.domain.repository.auth.AuthRepository
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import com.paymong.wear.domain.repository.notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainConfigureViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    val processCode: LiveData<MainConfigureProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(MainConfigureProcessCode.STAND_BY)

    fun resetProcessCode() {
        _processCode.postValue(MainConfigureProcessCode.STAND_BY)
    }

    fun mongsLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                authRepository.logout()
                _processCode.postValue(MainConfigureProcessCode.LOGOUT_SUCCESS)

                notificationRepository.disconnectNotification()
                notificationRepository.resetNotification()

                _processCode.postValue(MainConfigureProcessCode.NAV_INIT_LANDING)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.AUTH.groupCode,
                    location = "MainConfigureViewModel#mongsLogout",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(MainConfigureProcessCode.MONGS_LOGOUT_FAIL)
            }
        }
    }

    fun googleSignOut() {
        _processCode.postValue(MainConfigureProcessCode.LOGOUT)
    }
    fun googleSignOutFail() {
        viewModelScope.launch(Dispatchers.IO) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.AUTH.groupCode,
                location = "MainConfigureViewModel#googleSignOutFail",
                message = AuthErrorCode.LOGOUT_FAIL.message(),
            )
            _processCode.postValue(MainConfigureProcessCode.GOOGLE_SIGN_OUT_FAIL)
        }
    }
}