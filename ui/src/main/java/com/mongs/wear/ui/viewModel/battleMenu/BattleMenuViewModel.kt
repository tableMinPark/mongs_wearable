package com.mongs.wear.ui.viewModel.battleMenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.battle.MatchWaitCancelUseCase
import com.mongs.wear.domain.usecase.battle.MatchWaitUseCase
import com.mongs.wear.domain.vo.MatchVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleMenuViewModel @Inject constructor(
    private val matchWaitUseCase: MatchWaitUseCase,
    private val matchWaitCancelUseCase: MatchWaitCancelUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var matchVo: LiveData<MatchVo> = MutableLiveData(MatchVo())

    fun matchWait() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                matchWaitUseCase()
            } catch (e: UseCaseException) {
                e.printStackTrace()
                uiState.loadingBar = false
            }
        }
    }

    fun matchWaitCancel() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                matchWaitCancelUseCase()
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        CoroutineScope(Dispatchers.IO).launch {
            matchWaitCancelUseCase()
            super.onCleared()
        }
    }

    class UiState (
        navBattleMatchView: Boolean = false,
        loadingBar: Boolean = false,
    ) {
        var navBattleMatchView by mutableStateOf(navBattleMatchView)
        var loadingBar by mutableStateOf(loadingBar)
    }
}