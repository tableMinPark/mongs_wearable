package com.mongs.wear.ui.viewModel.feedFoodPick

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.feed.FeedUseCase
import com.mongs.wear.domain.usecase.feed.GetFoodCodesUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotPayPointUseCase
import com.mongs.wear.domain.vo.FoodVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedFoodPickViewModel @Inject constructor(
    private val getFoodCodesUseCase: GetFoodCodesUseCase,
    private val getNowSlotPayPointUseCase: GetNowSlotPayPointUseCase,
    private val feedUseCase: FeedUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var payPoint: LiveData<Int> = MutableLiveData()
    val foodVoList: LiveData<List<FoodVo>> get() = _foodVoList
    private val _foodVoList = MutableLiveData<List<FoodVo>>()

    fun loadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                payPoint = getNowSlotPayPointUseCase()
                _foodVoList.postValue(getFoodCodesUseCase())
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                uiState.navFeedMenu = true
            }
        }
    }

    fun buyFood(foodCode: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.buyDialog = false
                feedUseCase(code = foodCode)
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