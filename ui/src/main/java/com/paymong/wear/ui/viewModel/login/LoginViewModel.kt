package com.paymong.wear.ui.viewModel.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    fun googleLogin() {

    }

    fun login(email: String?, name: String?) {
        viewModelScope.launch (Dispatchers.IO) {
            delay(2000)
            val isSuccess = loginUseCase(email!!, name!!)
            Log.d("LoginViewModel.login", "isSuccess: $isSuccess")

            if (isSuccess) {
                uiState.navMainPagerView = true
            } else {
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