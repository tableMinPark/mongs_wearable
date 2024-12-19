package com.mongs.wear.presentation.common

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

abstract class BaseViewModel : ViewModel() {

    companion object {
        private const val TOAST_DELAY = 2000L
    }

    private val _errorMessage = MutableLiveData("")
    val errorMessage: MutableLiveData<String> get() = _errorMessage

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

        Log.e("exceptionHandler", "${exception.message}")

        CoroutineScope(Dispatchers.IO).launch {
            if (exception is ErrorException) {
                _errorMessage.postValue(exception.message)
                exceptionHandler(exception = exception, loadingBar = false, errorToast = true)
                withTimeout(TOAST_DELAY) { exceptionHandler(exception = exception, errorToast = false) }
            } else {
                exceptionHandler(exception = exception, loadingBar = false, errorToast = true)
            }
        }
    }

    protected val viewModelScopeWithHandler = CoroutineScope(
        viewModelScope.coroutineContext + exceptionHandler
    )

    abstract fun exceptionHandler(exception: Throwable, loadingBar: Boolean = false, errorToast: Boolean = false)

    open class BaseUiState(
        loadingBar: Boolean = false,
        errorToast: Boolean = false,
    ) {

        var loadingBar by mutableStateOf(loadingBar)
        var errorToast by mutableStateOf(errorToast)
    }
}