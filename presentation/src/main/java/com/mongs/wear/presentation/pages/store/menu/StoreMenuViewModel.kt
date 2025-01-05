package com.mongs.wear.presentation.pages.store.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mongs.wear.domain.management.exception.GetCurrentSlotException
import com.mongs.wear.domain.management.usecase.GetCurrentSlotUseCase
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.presentation.global.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StoreMenuViewModel @Inject constructor(
    private val getCurrentSlotUseCase: GetCurrentSlotUseCase,
) : BaseViewModel() {

    private val _mongVo = MediatorLiveData<MongVo?>(null)
    val mongVo: LiveData<MongVo?> get() = _mongVo

    init {
        viewModelScopeWithHandler.launch(Dispatchers.Main) {

            uiState.loadingBar = true

            _mongVo.addSource(withContext(Dispatchers.IO) { getCurrentSlotUseCase() }) {
                it?.let { mongVo ->
                    _mongVo.value = mongVo
                }
            }

            uiState.loadingBar = false
        }
    }

    val uiState = UiState()

    class UiState : BaseUiState() {
    }

    override fun exceptionHandler(exception: Throwable) {

        when(exception) {
            is GetCurrentSlotException -> {
                uiState.loadingBar = false
            }

            else -> {
                uiState.loadingBar = false
            }
        }
    }
}