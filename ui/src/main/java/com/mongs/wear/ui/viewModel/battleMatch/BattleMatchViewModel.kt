package com.mongs.wear.ui.viewModel.battleMatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.code.BattlePickCode
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.battle.GetMatchUseCase
import com.mongs.wear.domain.usecase.battle.GetMyMatchPlayerUseCase
import com.mongs.wear.domain.usecase.battle.GetOtherMatchPlayerUseCase
import com.mongs.wear.domain.usecase.battle.MatchExitUseCase
import com.mongs.wear.domain.usecase.battle.MatchOverUseCase
import com.mongs.wear.domain.usecase.battle.MatchStartUseCase
import com.mongs.wear.domain.usecase.battle.MatchPickUseCase
import com.mongs.wear.domain.vo.MatchPlayerVo
import com.mongs.wear.domain.vo.MatchVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleMatchViewModel @Inject constructor(
    private val getMatchUseCase: GetMatchUseCase,
    private val getMyMatchPlayerUseCase: GetMyMatchPlayerUseCase,
    private val getOtherMatchPlayerUseCase: GetOtherMatchPlayerUseCase,
    private val matchStartUseCase: MatchStartUseCase,
    private val matchPickUseCase: MatchPickUseCase,
    private val matchOverUseCase: MatchOverUseCase,
    private val matchExitUseCase: MatchExitUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var matchVo: LiveData<MatchVo> = MutableLiveData()
    var myMatchPlayerVo: LiveData<MatchPlayerVo> = MutableLiveData()
    var otherMatchPlayerVo: LiveData<MatchPlayerVo> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                matchVo = getMatchUseCase()
                myMatchPlayerVo = getMyMatchPlayerUseCase()
                otherMatchPlayerVo = getOtherMatchPlayerUseCase()
                uiState.isLoading = false
            } catch (_: UseCaseException) {
            }
        }
    }

    fun matchStart() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                matchStartUseCase()
            } catch (_: UseCaseException) {
            }
        }
    }

    fun matchPick(pickCode: BattlePickCode) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                matchPickUseCase(pickCode)
                uiState.matchPickDialog = false
            } catch (_: UseCaseException) {
            }
        }
    }

    fun matchOver() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                matchOverUseCase()
                uiState.matchPickDialog = false
            } catch (_: UseCaseException) {
            }
        }
    }

    fun matchExit() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                matchExitUseCase()
            } catch (_: UseCaseException) {
            }
        }
    }

    class UiState (
        isLoading: Boolean = true,
        matchPickDialog: Boolean = false,
    ) {
        var isLoading by mutableStateOf(isLoading)
        var matchPickDialog by mutableStateOf(matchPickDialog)
    }
}