package com.mongs.wear.presentation.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.usecase.LoginUseCase
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

            try {
                loginUseCase(email, name)
                uiState.navMainPagerView = true

            } catch (e: ErrorException) {

                uiState.loadingBar = false
                uiState.signInButton = true
            }
        }
    }

    fun loginSuccess(email: String?, name: String?) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                loginUseCase(email, name)
                uiState.navMainPagerView = true

            } catch (e: ErrorException) {

                uiState.loadingBar = false
                uiState.signInButton = true
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