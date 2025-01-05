package com.mongs.wear.presentation.pages.main.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.domain.auth.exception.LogoutException
import com.mongs.wear.domain.auth.usecase.LogoutUseCase
import com.mongs.wear.presentation.global.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainConfigureViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
): BaseViewModel() {

    init {
        viewModelScopeWithHandler.launch(Dispatchers.Main) {
            uiState.loadingBar = false
        }
    }

    /**
     * 로그아웃
     */
    fun logout() {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {
            logoutUseCase()
            scrollPageMainPagerView()
            uiState.navLoginView = true
        }
    }

    val uiState: UiState = UiState()

    class UiState : BaseUiState() {
        var navLoginView by mutableStateOf(false)
        var logoutDialog by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        when(exception) {
            is LogoutException -> {
                uiState.loadingBar = false
                uiState.logoutDialog = false
            }

            else -> {
                uiState.loadingBar = false
            }
        }
    }
}