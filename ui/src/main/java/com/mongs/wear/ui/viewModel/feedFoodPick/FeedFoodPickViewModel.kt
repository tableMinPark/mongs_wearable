package com.mongs.wear.ui.viewModel.feedFoodPick

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.parent.UseCaseException
import com.mongs.wear.domain.usecase.feed.FeedUseCase
import com.mongs.wear.domain.usecase.feed.GetFoodCodesUseCase
import com.mongs.wear.domain.vo.FoodVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedFoodPickViewModel @Inject constructor(
    private val getFoodCodesUseCase: GetFoodCodesUseCase,
    private val feedUseCase: FeedUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    val foodVoList: LiveData<List<FoodVo>> get() = _foodVoList
    private val _foodVoList = MutableLiveData<List<FoodVo>>()
    var payPoint: LiveData<Int> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                _foodVoList.postValue(getFoodCodesUseCase())
                delay(800)
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                e.printStackTrace()
                uiState.navFeedMenu = true
            }
        }
    }

    fun buyFood(foodCode: String) {
        viewModelScope.launch (Dispatchers.IO) {
            feedUseCase(code = foodCode)
            delay(800)
            uiState.navMainPager = true
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