package com.mongs.wear.presentation.global.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.UseCaseException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    companion object {

        private const val TAG = "BaseViewModel"

        private val _errorEvent = MutableSharedFlow<String>()
        val errorEvent = _errorEvent.asSharedFlow()

        private val _pageScrollMainPagerViewEvent = MutableSharedFlow<Unit>()
        val pageScrollMainPagerViewEvent = _pageScrollMainPagerViewEvent.asSharedFlow()

        suspend fun scrollPageMainPagerView() {
            _pageScrollMainPagerViewEvent.emit(Unit)
        }

        val effectState = BaseEffectState()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

        CoroutineScope(Dispatchers.IO).launch {

            if (exception is UseCaseException) {
                exceptionHandler(exception = exception)
                Log.e(TAG, "[${exception.javaClass.simpleName}] ${exception.code} -> ${exception.message} ===> ${exception.result}")

                if (exception.code.isMessageShow()) {
                    _errorEvent.emit(exception.message)
                }

            } else {
                exceptionHandler(exception = exception)
                Log.e(TAG, "[${exception.javaClass.simpleName}] ${exception.message}")
            }
        }
    }

    protected val viewModelScopeWithHandler = CoroutineScope(
        viewModelScope.coroutineContext + exceptionHandler
    )

    abstract fun exceptionHandler(exception: Throwable)

    open class BaseUiState {
        var loadingBar by mutableStateOf(true)
    }

    open class BaseEffectState {

        companion object {
            private const val EFFECT_DELAY = 2000L
        }

        var isHappy by mutableStateOf(false)
        var isEating by mutableStateOf(false)
        var isPoopCleaning by mutableStateOf(false)

        fun happyEffect() {
            CoroutineScope(Dispatchers.IO).launch {
                isHappy = true
                delay(EFFECT_DELAY)
                isHappy = false
            }
        }

        fun poopCleaningEffect() {
            CoroutineScope(Dispatchers.IO).launch {
                isPoopCleaning = true
                delay(EFFECT_DELAY)
                isPoopCleaning = false
            }
        }

        fun eatingEffect() {
            CoroutineScope(Dispatchers.IO).launch {
                isEating = true
                delay(EFFECT_DELAY)
                isEating = false
            }
        }
    }
}