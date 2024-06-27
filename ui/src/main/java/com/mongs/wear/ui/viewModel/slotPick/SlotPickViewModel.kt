package com.mongs.wear.ui.viewModel.slotPick

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.member.BuySlotUseCase
import com.mongs.wear.domain.usecase.member.GetMaxSlotUseCase
import com.mongs.wear.domain.usecase.member.GetStarPointUseCase
import com.mongs.wear.domain.usecase.slot.AddSlotUseCase
import com.mongs.wear.domain.usecase.slot.GetSlotsUseCase
import com.mongs.wear.domain.usecase.slot.RemoveSlotUseCase
import com.mongs.wear.domain.usecase.slot.SetNowSlotUseCase
import com.mongs.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    val starPoint: LiveData<Int> get() = _starPoint
    private val _starPoint = MediatorLiveData<Int>()
    val slotVoList: LiveData<List<SlotVo>> get() = _slotVoList
    private val _slotVoList = MediatorLiveData<List<SlotVo>>()
    val maxSlot: LiveData<Int> get() = _maxSlot
    private val _maxSlot = MediatorLiveData<Int>()
    val buySlotPrice: LiveData<Int> get() = _buySlotPrice
    private val _buySlotPrice = MutableLiveData<Int>()

    fun loadData() {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                _starPoint.addSource(
                    withContext(Dispatchers.IO) {
                        getStarPointUseCase()
                    }
                ) { starPoint ->
                    _starPoint.value = starPoint
                }

                _slotVoList.addSource(
                    withContext(Dispatchers.IO) {
                        getSlotsUseCase()
                    }
                ) { slotVoList ->
                    _slotVoList.value = slotVoList
                }

                _maxSlot.addSource(
                    withContext(Dispatchers.IO) {
                        getMaxSlotUseCase()
                    }
                ) { maxSlot ->
                    _maxSlot.value = maxSlot
                }

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