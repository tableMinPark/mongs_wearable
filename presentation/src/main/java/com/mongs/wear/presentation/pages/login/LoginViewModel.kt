package com.mongs.wear.presentation.pages.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.errors.CommonErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.usecase.JoinUseCase
import com.mongs.wear.domain.auth.usecase.LoginUseCase
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val joinUseCase: JoinUseCase,
    private val loginUseCase: LoginUseCase,
): BaseViewModel() {

    /**
     * 구글 로그인 확인
     */
    fun login() {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {

            uiState.loadingBar = true

            GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
                loginUseCase(googleAccountId = account.id, email = account.email)
                uiState.navMainPagerView = true
            } ?: run {
                uiState.loadingBar = false
                uiState.signInButton = true
            }
        }
    }

    /**
     * 구글 로그인 완료
     */
    fun login(googleSignInResult: ActivityResult) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            if (googleSignInResult.resultCode == Activity.RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(googleSignInResult.data).result?.let { account ->
                    loginUseCase(account.id, account.email)
                    uiState.navMainPagerView = true
                }
            } else {
                uiState.loadingBar = false
                uiState.signInButton = true
            }
        }
    }

    /**
     * 회원 가입 & 로그인
     */
    private fun joinAndLogin() {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            GoogleSignIn.getLastSignedInAccount(context)?.let { account ->
                joinUseCase(googleAccountId = account.id, email = account.email, name = account.displayName)
                loginUseCase(googleAccountId = account.id, email = account.email)
                uiState.navMainPagerView = true
            } ?: run {
                uiState.loadingBar = false
                uiState.signInButton = true
            }
        }
    }

    /**
     * 로그인 버튼
     */
    fun loginButtonClick(googleLoginLauncher: ActivityResultLauncher<Intent>) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {

            uiState.loadingBar = true
            uiState.signInButton = false

            val googleSignIn = GoogleSignIn.getLastSignedInAccount(context)
            val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOption)

            if (googleSignIn != null) {
                googleSignInClient.signOut()
            }

            googleLoginLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    /**
     * UI 상태
     */
    val uiState: UiState = UiState()

    class UiState : BaseUiState() {

        // 로딩바
        var loadingBar by mutableStateOf(false)

        // 로그인 버튼
        var signInButton by mutableStateOf(false)

        // 앱 업데이트 화면 표출
        var needAppUpdate by mutableStateOf(false)

        // 메인 화면 이동
        var navMainPagerView by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {

                // 로그인
                AuthErrorCode.DOMAIN_AUTH_NOT_EXISTS_NAME,
                AuthErrorCode.DOMAIN_AUTH_NOT_EXISTS_EMAIL,
                AuthErrorCode.DATA_AUTH_LOGIN -> {
                    uiState.loadingBar = false
                    uiState.signInButton = true
                }

                // 회원 가입 필요
                AuthErrorCode.DATA_AUTH_NEED_JOIN -> {
                    joinAndLogin()
                }

                // 회원 가입 실패
                AuthErrorCode.DATA_AUTH_JOIN -> {
                    uiState.loadingBar = false
                    uiState.signInButton = true
                }

                // 앱 업데이트 여부
                AuthErrorCode.DATA_AUTH_NEED_UPDATE_APP -> {
                    uiState.loadingBar = false
                    uiState.needAppUpdate = true
                }

                CommonErrorCode.DATA_COMMON_MQTT_SUB -> {
                    uiState.loadingBar = false
                    uiState.signInButton = true
                }

                else -> {
                    uiState.loadingBar = false
                    uiState.signInButton = true
                }
            }
        }
    }
}