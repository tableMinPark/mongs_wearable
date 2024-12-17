package com.mongs.wear.presentation.pages.feed.snack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.usecase.feed.FeedUseCase
import com.mongs.wear.domain.usecase.feed.GetSnackCodesUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotPayPointUseCase
import com.mongs.wear.domain.vo.SnackVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedSnackPickViewModel @Inject constructor(
    private val getSnackCodesUseCase: GetSnackCodesUseCase,
    private val getNowSlotPayPointUseCase: GetNowSlotPayPointUseCase,
    private val feedUseCase: FeedUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    val payPoint: LiveData<Int> get() = _payPoint
    private val _payPoint = MediatorLiveData<Int>()
    val snackVoList: LiveData<List<SnackVo>> get() = _snackVoList
    private val _snackVoList = MutableLiveData<List<SnackVo>>()

    init {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                _payPoint.addSource(
                    withContext(Dispatchers.IO) {
                        getNowSlotPayPointUseCase()
                    }
                ) { payPoint ->
                    _payPoint.value = payPoint
                }

                val snackVoList = withContext(Dispatchers.IO) {
                    getSnackCodesUseCase()
                }
                _snackVoList.postValue(snackVoList)


                uiState.loadingBar = false
            } catch (_: ErrorException) {
                uiState.navFeedMenu = true
            }
        }
    }

    fun buySnack(snackCode: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                feedUseCase(code = snackCode)
                uiState.navMainPager = true
                uiState.buyDialog = false
            } catch (_: ErrorException) {
                uiState.navMainPager = true
                uiState.buyDialog = false
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