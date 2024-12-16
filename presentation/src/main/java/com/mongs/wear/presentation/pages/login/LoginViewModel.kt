package com.mongs.wear.presentation.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
): ViewModel() {

    val uiState: UiState = UiState()

    fun loginFail() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.loadingBar = false
            uiState.signInButton = true
        }
    }

    fun reLoginSuccess(email: String?, name: String?) {

        viewModelScope.launch(Dispatchers.IO) {

            if (email.isNullOrEmpty() || name.isNullOrEmpty()) {
                uiState.loadingBar = false
                uiState.signInButton = true
            } else {
                loginUseCase(email, name)
                uiState.navMainPagerView = true
            }
        }
    }

    fun loginSuccess(email: String?, name: String?) {

        viewModelScope.launch(Dispatchers.IO) {

            if (email.isNullOrEmpty() || name.isNullOrEmpty()) {
                uiState.loadingBar = false
                uiState.signInButton = true
            } else {
                loginUseCase(email, name)
                uiState.navMainPagerView = true
            }
        }
    }

    class UiState (
        navMainPagerView: Boolean = false,
        loadingBar: Boolean = true,
        signInButton: Boolean = false,
    ) {
        var navMainPagerView by mutableStateOf(navMainPagerView)
        var loadingBar by mutableStateOf(loadingBar)
        var signInButton by mutableStateOf(signInButton)
    }
}