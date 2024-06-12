package com.mongs.wear.ui.viewModel.slotPick

import android.util.Log
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
import com.mongs.wear.domain.usecase.member.BuySlotUseCase
import com.mongs.wear.domain.usecase.member.GetMaxSlotUseCase
import com.mongs.wear.domain.usecase.member.GetStarPointUseCase
import com.mongs.wear.domain.usecase.slot.AddSlotUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotPayPointUseCase
import com.mongs.wear.domain.usecase.slot.GetSlotsUseCase
import com.mongs.wear.domain.usecase.slot.RemoveSlotUseCase
import com.mongs.wear.domain.usecase.slot.SetNowSlotUseCase
import com.mongs.wear.domain.vo.FoodVo
import com.mongs.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SlotPickViewModel @Inject constructor(
    private val addSlotUseCase: AddSlotUseCase,
    private val removeSlotUseCase: RemoveSlotUseCase,
    private val setNowSlotUseCase: SetNowSlotUseCase,
    private val buySlotUseCase: BuySlotUseCase,
    private val getStarPointUseCase: GetStarPointUseCase,
    private val getMaxSlotUseCase: GetMaxSlotUseCase,
    private val getSlotsUseCase: GetSlotsUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var starPoint: LiveData<Int> = MutableLiveData()
    var slotVoList: LiveData<List<SlotVo>> = MutableLiveData()
    var maxSlot: LiveData<Int> = MutableLiveData()
    val buySlotPrice: LiveData<Int> get() = _buySlotPrice
    private val _buySlotPrice = MutableLiveData<Int>()
    val slotVo: LiveData<SlotVo> get() = _slotVo
    private val _slotVo = MutableLiveData<SlotVo>()

    fun loadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                starPoint = getStarPointUseCase()
                slotVoList = getSlotsUseCase()
                maxSlot = getMaxSlotUseCase()
                _buySlotPrice.postValue(10)
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                uiState.navMainPager = true
            }
        }
    }

    fun addMong(name: String, sleepStart: String, sleepEnd: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                addSlotUseCase(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
            } catch (_: UseCaseException) {
            } finally {
                uiState.addDialog = false
                uiState.loadingBar = false
            }
        }
    }

    fun deleteMong(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                removeSlotUseCase(mongId = mongId)
            } catch (_: UseCaseException) {
            } finally {
                uiState.loadingBar = false
                uiState.deleteDialog = false
            }
        }
    }

    fun pickMong(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                setNowSlotUseCase(mongId = mongId)
                uiState.navMainPager = true
            } catch (_: UseCaseException) {
                uiState.loadingBar = false
                uiState.pickDialog = false
            }
        }
    }

    fun buySlot() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                buySlotUseCase()
            } catch (_: UseCaseException) {
            } finally {
                uiState.loadingBar = false
                uiState.buySlotDialog = false
            }
        }
    }

    class UiState (
        navMainPager: Boolean = false,
        loadingBar: Boolean = true,
        addDialog: Boolean = false,
        deleteDialog: Boolean = false,
        pickDialog: Boolean = false,
        buySlotDialog: Boolean = false,
        detailDialog: Boolean = false,
    ) {
        var navMainPager by mutableStateOf(navMainPager)
        var loadingBar by mutableStateOf(loadingBar)
        var addDialog by mutableStateOf(addDialog)
        var deleteDialog by mutableStateOf(deleteDialog)
        var pickDialog by mutableStateOf(pickDialog)
        var buySlotDialog by mutableStateOf(buySlotDialog)
        var detailDialog by mutableStateOf(detailDialog)
    }
}