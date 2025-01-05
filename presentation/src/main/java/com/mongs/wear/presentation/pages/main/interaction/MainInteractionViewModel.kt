package com.mongs.wear.presentation.pages.main.interaction

import com.mongs.wear.domain.management.exception.PoopCleanMongException
import com.mongs.wear.domain.management.exception.SleepingMongException
import com.mongs.wear.domain.management.usecase.PoopCleanMongUseCase
import com.mongs.wear.domain.management.usecase.SleepingMongUseCase
import com.mongs.wear.presentation.global.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainInteractionViewModel @Inject constructor(
    private val sleepingMongUseCase: SleepingMongUseCase,
    private val poopCleanMongUseCase: PoopCleanMongUseCase,
): BaseViewModel() {

    fun sleeping(mongId: Long) {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {
            sleepingMongUseCase(
                SleepingMongUseCase.Param(
                    mongId = mongId
                )
            )

            scrollPageMainPagerView()
        }
    }

    fun poopClean(mongId: Long) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {

            poopCleanMongUseCase(
                PoopCleanMongUseCase.Param(
                    mongId = mongId
                )
            )

            scrollPageMainPagerView()

            effectState.poopCleaningEffect()
        }
    }

    val uiState: UiState = UiState()

    class UiState : BaseUiState() {}

    override fun exceptionHandler(exception: Throwable) {

        when(exception) {
            is SleepingMongException -> {
                uiState.loadingBar = false
            }

            is PoopCleanMongException -> {
                uiState.loadingBar = false
            }

            else -> {
                uiState.loadingBar = false
            }
        }
    }
}