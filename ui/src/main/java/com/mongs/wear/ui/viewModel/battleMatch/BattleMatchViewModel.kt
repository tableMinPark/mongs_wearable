package com.mongs.wear.ui.viewModel.battleMatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.battle.GetMatchUseCase
import com.mongs.wear.domain.usecase.battle.GetMyMatchPlayerUseCase
import com.mongs.wear.domain.usecase.battle.GetOtherMatchPlayerUseCase
import com.mongs.wear.domain.usecase.battle.MatchExitUseCase
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

    class UiState (
        isLoading: Boolean = true,
    ) {
        var isLoading by mutableStateOf(isLoading)
    }

    override fun onCleared() {
        CoroutineScope(Dispatchers.IO).launch {
            matchExitUseCase()
            super.onCleared()
        }
    }
}