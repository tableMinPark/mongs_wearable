package com.mongs.wear.presentation.pages.main.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.core.errors.AuthErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.usecase.LogoutUseCase
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainConfigureViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
): BaseViewModel() {

    /**
     * 로그아웃
     */
    fun logout() {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {
            logoutUseCase()
            uiState.navLoginView = true
        }
    }

    val uiState: UiState = UiState()

    class UiState (
        loadingBar: Boolean = false,
        navLoginView: Boolean = false,
        logoutDialog: Boolean = false,
    ) : BaseUiState() {

        var loadingBar by mutableStateOf(loadingBar)
        var navLoginView by mutableStateOf(navLoginView)
        var logoutDialog by mutableStateOf(logoutDialog)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {

                AuthErrorCode.DATA_AUTH_LOGOUT -> {
                    uiState.logoutDialog = false
                }
            }
        }
    }
}