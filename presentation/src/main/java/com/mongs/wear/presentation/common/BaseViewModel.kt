package com.mongs.wear.presentation.common

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    companion object {
        var errorToast by mutableStateOf(false)
        var errorMessage by mutableStateOf("")
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

        CoroutineScope(Dispatchers.IO).launch {

            if (exception is ErrorException) {
                exceptionHandler(exception = exception)
                Log.e("viewModelExceptionHandler", "[${exception.javaClass.simpleName}] ${exception.code} -> ${exception.message} ===> ${exception.result}")

                errorMessage = exception.message
                errorToast = true

            } else {
                exceptionHandler(exception = exception)
                Log.e("viewModelExceptionHandler", "[${exception.javaClass.simpleName}] ${exception.message}")
            }
        }
    }

    protected val viewModelScopeWithHandler = CoroutineScope(
        viewModelScope.coroutineContext + exceptionHandler
    )

    abstract fun exceptionHandler(exception: Throwable)

    open class BaseUiState
}