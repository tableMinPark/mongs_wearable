package com.mongs.wear.ui.viewModel.battleMenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.battle.GetBattleRoomUseCase
import com.mongs.wear.domain.usecase.battle.MatchExitUseCase
import com.mongs.wear.domain.usecase.battle.MatchSearchUseCase
import com.mongs.wear.domain.vo.BattleRoomVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleMenuViewModel @Inject constructor(
    private val getBattleRoomUseCase: GetBattleRoomUseCase,
    private val matchSearchUseCase: MatchSearchUseCase,
    private val matchExitUseCase: MatchExitUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var battleRoomVo: LiveData<BattleRoomVo> = MutableLiveData(BattleRoomVo())

    fun matchSearch() {
        val timeoutJob: Job  = viewModelScope.launch (Dispatchers.IO) {
            try {
                delay(300000)
                matchExitUseCase()
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                uiState.loadingBar = false
            }
        }

        viewModelScope.launch (Dispatchers.IO) {
            try {
                battleRoomVo = getBattleRoomUseCase()
                matchSearchUseCase()
                timeoutJob.start()
            } catch (e: UseCaseException) {
                uiState.loadingBar = false
            }
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