package com.mongs.wear.presentation.pages.main.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.auth.usecase.LogoutUseCase
import com.mongs.wear.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainConfigureViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
): BaseViewModel() {

    fun logout() {
        viewModelScope.launch (Dispatchers.IO) {
            logoutUseCase()
            uiState.navLoginView = true
        }
    }

    val uiState: UiState = UiState()

    class UiState (
        navLoginView: Boolean = false,
        logoutDialog: Boolean = false,
    ) : BaseUiState() {

        var navLoginView by mutableStateOf(navLoginView)
        var logoutDialog by mutableStateOf(logoutDialog)
    }

    override fun exceptionHandler(exception: Throwable, loadingBar: Boolean, errorToast: Boolean) {

        uiState.loadingBar = loadingBar
        uiState.errorToast = errorToast

        if (exception is ErrorException) {
            uiState.logoutDialog = false
        }
    }
}