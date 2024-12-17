package com.mongs.wear.presentation.pages.slot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.usecase.member.BuySlotUseCase
import com.mongs.wear.domain.usecase.member.GetMaxSlotUseCase
import com.mongs.wear.domain.usecase.member.GetStarPointUseCase
import com.mongs.wear.domain.usecase.slot.AddSlotUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotUseCase
import com.mongs.wear.domain.usecase.slot.GetSlotsUseCase
import com.mongs.wear.domain.usecase.slot.GraduateSlotUseCase
import com.mongs.wear.domain.usecase.slot.RemoveSlotUseCase
import com.mongs.wear.domain.usecase.slot.SetNowSlotUseCase
import com.mongs.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SlotPickViewModel @Inject constructor(
    private val getNowSlotUseCase: GetNowSlotUseCase,
    private val addSlotUseCase: AddSlotUseCase,
    private val removeSlotUseCase: RemoveSlotUseCase,
    private val setNowSlotUseCase: SetNowSlotUseCase,
    private val buySlotUseCase: BuySlotUseCase,
    private val graduateSlotUseCase: GraduateSlotUseCase,
    private val getStarPointUseCase: GetStarPointUseCase,
    private val getMaxSlotUseCase: GetMaxSlotUseCase,
    private val getSlotsUseCase: GetSlotsUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    val slotVo: LiveData<SlotVo?> get() = _slotVo
    private val _slotVo = MediatorLiveData<SlotVo?>(null)
    val starPoint: LiveData<Int> get() = _starPoint
    private val _starPoint = MediatorLiveData<Int>()
    val slotVoList: LiveData<List<SlotVo>> get() = _slotVoList
    private val _slotVoList = MediatorLiveData<List<SlotVo>>()
    val maxSlot: LiveData<Int> get() = _maxSlot
    private val _maxSlot = MediatorLiveData<Int>()
    val buySlotPrice: LiveData<Int> get() = _buySlotPrice
    private val _buySlotPrice = MutableLiveData<Int>()

    init {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                uiState.loadingBar = true

                _slotVo.addSource(
                    withContext(Dispatchers.IO) {
                        getNowSlotUseCase()
                    }
                ) { slotVo ->
                    _slotVo.value = slotVo
                }

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

            } catch (e: ErrorException) {
                uiState.navMainPager = true
            }
        }
    }

    fun addMong(name: String, sleepStart: String, sleepEnd: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.addDialog = false
                uiState.loadingBar = true
                addSlotUseCase(name = name, sleepStart = sleepStart, sleepEnd = sleepEnd)
                uiState.loadingBar = false
            } catch (_: ErrorException) {
                uiState.loadingBar = false
            }
        }
    }

    fun deleteMong(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.deleteDialog = false
                uiState.loadingBar = true
                removeSlotUseCase(mongId = mongId)
                uiState.loadingBar = false
            } catch (_: ErrorException) {
                uiState.loadingBar = false
            }
        }
    }

    fun pickMong(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.pickDialog = false
                uiState.loadingBar = true
                setNowSlotUseCase(mongId = mongId)
                uiState.navMainPager = true
            } catch (e: ErrorException) {
                uiState.loadingBar = false
            }
        }
    }

    fun graduateMong(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.graduateDialog = false
                uiState.loadingBar = true
                graduateSlotUseCase(mongId = mongId)
                uiState.loadingBar = false
            } catch (e: ErrorException) {
                uiState.loadingBar = false
            }
        }
    }

    fun buySlot() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.buySlotDialog = false
                uiState.loadingBar = true
                buySlotUseCase()
                uiState.loadingBar = false
            } catch (_: ErrorException) {
                uiState.loadingBar = false
            }
        }
    }

    class UiState (
        navMainPager: Boolean = false,
        loadingBar: Boolean = true,
        addDialog: Boolean = false,
        deleteDialog: Boolean = false,
        pickDialog: Boolean = false,
        graduateDialog: Boolean = false,
        buySlotDialog: Boolean = false,
        detailDialog: Boolean = false,
    ) {
        var navMainPager by mutableStateOf(navMainPager)
        var loadingBar by mutableStateOf(loadingBar)
        var addDialog by mutableStateOf(addDialog)
        var deleteDialog by mutableStateOf(deleteDialog)
        var pickDialog by mutableStateOf(pickDialog)
        var graduateDialog by mutableStateOf(graduateDialog)
        var buySlotDialog by mutableStateOf(buySlotDialog)
        var detailDialog by mutableStateOf(detailDialog)
    }
}