package com.mongs.wear.ui.viewModel.mainConfigure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainConfigureViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    fun logout() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                logoutUseCase()
                uiState.navLoginView = true
            } catch (_: UseCaseException) {
                uiState.loadingBar = false
                uiState.logoutDialog = false
            }
        }
    }

    class UiState (
        navLoginView: Boolean = false,
        loadingBar: Boolean = false,
        logoutDialog: Boolean = false,
    ) {
        var navLoginView by mutableStateOf(navLoginView)
        var loadingBar by mutableStateOf(loadingBar)
        var logoutDialog by mutableStateOf(logoutDialog)
    }
}