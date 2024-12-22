package com.mongs.wear.presentation.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _errorMessage = MutableLiveData("")
    val errorMessage: MutableLiveData<String> get() = _errorMessage

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

        CoroutineScope(Dispatchers.IO).launch {

            if (exception is ErrorException) {
                _errorMessage.postValue(exception.message)
                exceptionHandler(exception = exception)

                Log.e("viewModelExceptionHandler", "[${exception.javaClass.simpleName}] ${exception.code} -> ${exception.message} ===> ${exception.result}")

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

    open class BaseUiState() {}
}