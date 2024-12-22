package com.mongs.wear.presentation.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.errors.CommonErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.usecase.JoinUseCase
import com.mongs.wear.domain.auth.usecase.LoginUseCase
import com.mongs.wear.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val joinUseCase: JoinUseCase,
    private val loginUseCase: LoginUseCase,
): BaseViewModel() {

    fun join(googleAccountId: String?, email: String?, name: String?) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            joinUseCase(googleAccountId = googleAccountId, email = email, name = name)
            loginUseCase(googleAccountId = googleAccountId, email = email)
            uiState.navMainPagerView = true
        }
    }

    fun login(googleAccountId: String?, email: String?) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            loginUseCase(googleAccountId = googleAccountId, email = email)
            uiState.navMainPagerView = true
        }
    }

    val uiState: UiState = UiState()

    class UiState (
        loadingBar: Boolean = false,
        navMainPagerView: Boolean = false,
        signInButton: Boolean = false,
        needAppUpdate: Boolean = false,
        needJoin: Boolean = false,
    ) : BaseUiState() {

        var loadingBar by mutableStateOf(loadingBar)
        var navMainPagerView by mutableStateOf(navMainPagerView)
        var signInButton by mutableStateOf(signInButton)
        var needAppUpdate by mutableStateOf(needAppUpdate)
        var needJoin by mutableStateOf(needJoin)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {

                // 로그인
                AuthErrorCode.AUTH_NOT_EXISTS_NAME, AuthErrorCode.AUTH_NOT_EXISTS_EMAIL -> {
                    uiState.signInButton = true
                }

                AuthErrorCode.AUTH_LOGIN -> {
                    uiState.signInButton = true
                    uiState.needJoin = true
                }

                AuthErrorCode.AUTH_NEED_JOIN -> {
                    uiState.needJoin = true
                }

                // 회원 가입
                AuthErrorCode.AUTH_JOIN -> {
                    uiState.signInButton = true
                }

                // 앱 업데이트 여부
                AuthErrorCode.AUTH_NEED_UPDATE_APP -> {
                    uiState.signInButton = false
                    uiState.needAppUpdate = true
                }

                CommonErrorCode.COMMON_MQTT_SUB -> {
                    uiState.signInButton = true
                }

                else -> {}
            }
        }
    }
}