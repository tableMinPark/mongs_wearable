package com.mongs.wear.presentation.pages.battle.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.battle.MatchWaitCancelUseCase
import com.mongs.wear.domain.usecase.battle.MatchWaitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleMenuViewModel @Inject constructor(
    private val matchWaitUseCase: MatchWaitUseCase,
    private val matchWaitCancelUseCase: MatchWaitCancelUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    fun matchWait() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.loadingBar = true

                matchWaitUseCase(
                    matchFindCallback = {
                        uiState.isMatchWait = false
                    },
                    matchEnterCallback = {
                        delay(1500)
                        uiState.navBattleMatchView = true
                    }
                )
            } catch (_: UseCaseException) {
                uiState.loadingBar = false
            }
        }
    }

    fun matchWaitCancel() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                matchWaitCancelUseCase()
                uiState.loadingBar = false
            } catch (_: UseCaseException) {
                uiState.loadingBar = false
            }
        }
    }

    class UiState (
        navBattleMatchView: Boolean = false,
        loadingBar: Boolean = false,
        isMatchWait: Boolean = true,
    ) {
        var navBattleMatchView by mutableStateOf(navBattleMatchView)
        var isMatchWait by mutableStateOf(isMatchWait)
        var loadingBar by mutableStateOf(loadingBar)
    }
}