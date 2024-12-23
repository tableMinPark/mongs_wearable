package com.mongs.wear.presentation.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.errors.CommonErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.usecase.JoinUseCase
import com.mongs.wear.domain.auth.usecase.LoginUseCase
import com.mongs.wear.domain.player.usecase.ResetWalkingCountUseCase
import com.mongs.wear.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val joinUseCase: JoinUseCase,
    private val loginUseCase: LoginUseCase,
    private val resetWalkingCountUseCase: ResetWalkingCountUseCase,
): BaseViewModel() {

    /**
     * 회원 가입
     */
    fun join(googleAccountId: String?, email: String?, name: String?) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            joinUseCase(googleAccountId = googleAccountId, email = email, name = name)
            loginUseCase(googleAccountId = googleAccountId, email = email)
            uiState.navMainPagerView = true
        }
    }

    /**
     * 로그인
     */
    fun login(googleAccountId: String?, email: String?) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            loginUseCase(googleAccountId = googleAccountId, email = email)
            resetWalkingCountUseCase()
            uiState.navMainPagerView = true
        }
    }

    /**
     * UI 상태
     */
    val uiState: UiState = UiState()

    class UiState : BaseUiState() {

        // 로딩바
        var loadingBar by mutableStateOf(false)

        // 메인 화면 이동
        var navMainPagerView by mutableStateOf(false)

        // 로그인 버튼
        var signInButton by mutableStateOf(false)

        // 앱 업데이트 화면 이동
        var needAppUpdate by mutableStateOf(false)

        // 회원 가입 필요
        var needJoin by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        uiState.loadingBar = false

        if (exception is ErrorException) {

            when (exception.code) {

                // 로그인
                AuthErrorCode.DOMAIN_AUTH_NOT_EXISTS_NAME, AuthErrorCode.DOMAIN_AUTH_NOT_EXISTS_EMAIL -> {
                    uiState.signInButton = true
                }

                AuthErrorCode.DATA_AUTH_LOGIN -> {
                    uiState.signInButton = true
                }

                AuthErrorCode.DATA_AUTH_NEED_JOIN -> {
                    uiState.needJoin = true
                }

                // 회원 가입
                AuthErrorCode.DATA_AUTH_JOIN -> {
                    uiState.signInButton = true
                }

                // 앱 업데이트 여부
                AuthErrorCode.DATA_AUTH_NEED_UPDATE_APP -> {
                    uiState.signInButton = false
                    uiState.needAppUpdate = true
                }

                CommonErrorCode.DATA_COMMON_MQTT_SUB -> {
                    uiState.signInButton = true
                }

                else -> {}
            }
        }
    }
}