package com.mongs.wear.presentation.pages.feed.food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mongs.wear.domain.management.exception.FoodCodesEmptyException
import com.mongs.wear.domain.management.usecase.FeedMongUseCase
import com.mongs.wear.domain.management.usecase.GetCurrentSlotUseCase
import com.mongs.wear.domain.management.usecase.GetFoodCodesUseCase
import com.mongs.wear.domain.management.vo.FeedItemVo
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.presentation.global.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedFoodPickViewModel @Inject constructor(
    private val getCurrentSlotUseCase: GetCurrentSlotUseCase,
    private val getFoodCodesUseCase: GetFoodCodesUseCase,
    private val feedMongUseCase: FeedMongUseCase,
): BaseViewModel() {

    val mongVo: LiveData<MongVo?> get() = _mongVo
    private val _mongVo = MediatorLiveData<MongVo?>(null)

    val foodVoList: LiveData<List<FeedItemVo>> get() = _foodVoList
    private val _foodVoList = MutableLiveData<List<FeedItemVo>>()

    init {
        viewModelScopeWithHandler.launch (Dispatchers.Main) {

            uiState.loadingBar = true

            _mongVo.addSource(withContext(Dispatchers.IO) { getCurrentSlotUseCase() }) { mongVo ->
                _mongVo.value = mongVo
            }

            _foodVoList.postValue(getFoodCodesUseCase())

            uiState.loadingBar = false
        }
    }

    fun buyFood(foodTypeCode: String) {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {

            uiState.loadingBar = true
            uiState.buyDialog = false

            feedMongUseCase(param = FeedMongUseCase.Param(foodTypeCode = foodTypeCode))

            _foodVoList.postValue(getFoodCodesUseCase())

            effectState.eatingEffect()

            scrollPageMainPagerView()
            uiState.navMainPager = true
        }
    }

    val uiState: UiState = UiState()

    class UiState : BaseUiState() {
        var buyDialog by mutableStateOf(false)
        var detailDialog by mutableStateOf(false)
        var navMainPager by mutableStateOf(false)
        var navFeedMenu by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        when(exception) {

            is FoodCodesEmptyException -> {
                uiState.navFeedMenu = true
            }

            else -> {
                uiState.loadingBar = false
                uiState.detailDialog = false
                uiState.buyDialog = false
            }
        }
    }
}