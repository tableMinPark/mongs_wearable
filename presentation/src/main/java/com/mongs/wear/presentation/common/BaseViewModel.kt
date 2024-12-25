package com.mongs.wear.presentation.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    companion object {
        private val _errorEvent = MutableSharedFlow<String>()
        val errorEvent = _errorEvent.asSharedFlow()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

        CoroutineScope(Dispatchers.IO).launch {

            exception.printStackTrace()

            if (exception is ErrorException) {
                exceptionHandler(exception = exception)
                Log.e("viewModelExceptionHandler", "[${exception.javaClass.simpleName}] ${exception.code} -> ${exception.message} ===> ${exception.result}")
                _errorEvent.emit(exception.message)

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