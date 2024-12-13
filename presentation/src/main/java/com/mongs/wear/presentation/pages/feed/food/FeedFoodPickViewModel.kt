package com.mongs.wear.presentation.pages.feed.food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.feed.FeedUseCase
import com.mongs.wear.domain.usecase.feed.GetFoodCodesUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotPayPointUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotUseCase
import com.mongs.wear.domain.vo.FoodVo
import com.mongs.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedFoodPickViewModel @Inject constructor(
    private val getNowSlotUseCase: GetNowSlotUseCase,
    private val getFoodCodesUseCase: GetFoodCodesUseCase,
    private val getNowSlotPayPointUseCase: GetNowSlotPayPointUseCase,
    private val feedUseCase: FeedUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    val slotVo: LiveData<SlotVo?> get() = _slotVo
    private val _slotVo = MediatorLiveData<SlotVo?>(null)
    val payPoint: LiveData<Int> get() = _payPoint
    private val _payPoint = MediatorLiveData<Int>()
    val foodVoList: LiveData<List<FoodVo>> get() = _foodVoList
    private val _foodVoList = MutableLiveData<List<FoodVo>>()

    init {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                uiState.loadingBar = true

                _slotVo.addSource( withContext(Dispatchers.IO) { getNowSlotUseCase() } ) {
                    slotVo -> _slotVo.value = slotVo
                }

                _payPoint.addSource( withContext(Dispatchers.IO) { getNowSlotPayPointUseCase() } ) {
                    payPoint -> _payPoint.value = payPoint
                }

                val foodVoList = withContext(Dispatchers.IO) {
                    getFoodCodesUseCase()
                }
                _foodVoList.postValue(foodVoList)

                uiState.loadingBar = false
            } catch (_: UseCaseException) {
                uiState.navFeedMenu = true
                uiState.loadingBar = false
            }
        }
    }

    fun buyFood(foodCode: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                feedUseCase(code = foodCode)
                uiState.navMainPager = true
                uiState.buyDialog = false
            } catch (_: UseCaseException) {
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