package com.mongs.wear.ui.viewModel.feedSnackPick

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.feed.FeedUseCase
import com.mongs.wear.domain.usecase.feed.GetSnackCodesUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotPayPointUseCase
import com.mongs.wear.domain.vo.SnackVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedSnackPickViewModel @Inject constructor(
    private val getSnackCodesUseCase: GetSnackCodesUseCase,
    private val getNowSlotPayPointUseCase: GetNowSlotPayPointUseCase,
    private val feedUseCase: FeedUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var payPoint: LiveData<Int> = MutableLiveData()
    val snackVoList: LiveData<List<SnackVo>> get() = _snackVoList
    private val _snackVoList = MutableLiveData<List<SnackVo>>()

    fun loadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                payPoint = getNowSlotPayPointUseCase()
                _snackVoList.postValue(getSnackCodesUseCase())
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                uiState.navFeedMenu = true
            }
        }
    }

    fun buySnack(snackCode: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.buyDialog = false
                feedUseCase(code = snackCode)
            } catch (_: UseCaseException) {
            } finally {
                uiState.navMainPager = true
            }
        }
    }


    class UiState (
        navMainPager: Boolean = false,
        navFeedMenu: Boolean = false,
        loadingBar: Boolean = true,
        detailDialog: Boolean = false,
        buyDialog: Boolean = false,
    ) {
        var navMainPager by mutableStateOf(navMainPager)
        var navFeedMenu by mutableStateOf(navFeedMenu)
        var loadingBar by mutableStateOf(loadingBar)
        var detailDialog by mutableStateOf(detailDialog)
        var buyDialog by mutableStateOf(buyDialog)
    }
}