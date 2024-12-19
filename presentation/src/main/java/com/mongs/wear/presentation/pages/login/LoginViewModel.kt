package com.mongs.wear.presentation.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.exception.InvalidLoginException
import com.mongs.wear.domain.auth.exception.InvalidLogoutException
import com.mongs.wear.domain.auth.usecase.LoginUseCase
import com.mongs.wear.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
): BaseViewModel() {

    fun loginSuccess(email: String?, name: String?) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            loginUseCase(email, name)
            uiState.navMainPagerView = true
        }
    }

    val uiState: UiState = UiState()

    class UiState (
        navMainPagerView: Boolean = false,
        signInButton: Boolean = false,
    ) : BaseUiState() {

        var navMainPagerView by mutableStateOf(navMainPagerView)
        var signInButton by mutableStateOf(signInButton)
    }

    override fun exceptionHandler(exception: Throwable, loadingBar: Boolean, errorToast: Boolean) {

        uiState.loadingBar = loadingBar
        uiState.errorToast = errorToast

        when (exception) {

            is InvalidLoginException -> {
                uiState.signInButton = true
            }

            is InvalidLogoutException -> {
                uiState.signInButton = true
            }

            else -> {}
        }
    }
}