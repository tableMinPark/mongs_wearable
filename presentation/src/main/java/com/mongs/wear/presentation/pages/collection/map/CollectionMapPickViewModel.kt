package com.mongs.wear.presentation.pages.collection.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.collection.usecase.GetMapCollectionsUseCase
import com.mongs.wear.domain.collection.vo.MapCollectionVo
import com.mongs.wear.domain.common.usecase.SetBackgroundMapCodeUseCase
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionMapPickViewModel @Inject constructor(
    private val getMapCollectionsUseCase: GetMapCollectionsUseCase,
    private val setBackgroundMapCodeUseCase: SetBackgroundMapCodeUseCase,
): BaseViewModel() {

    val mapCollectionVoList: LiveData<List<MapCollectionVo>> get() = _mapCollectionVoList
    private val _mapCollectionVoList = MutableLiveData<List<MapCollectionVo>>()

    init {
        viewModelScope.launch (Dispatchers.Main) {

            uiState.loadingBar = true

            _mapCollectionVoList.postValue(withContext(Dispatchers.IO) {
                getMapCollectionsUseCase()
            })

            uiState.loadingBar = false
        }
    }

    fun setBackground(mapCode: String) {
        viewModelScope.launch (Dispatchers.IO) {

            uiState.loadingBar = true

            setBackgroundMapCodeUseCase(code = mapCode)

            uiState.detailDialog = false
            uiState.loadingBar = false
        }
    }

    val uiState: UiState = UiState()

    class UiState : BaseUiState() {
        var navCollectionMenu by mutableStateOf(false)
        var loadingBar by mutableStateOf(false)
        var detailDialog by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {
                UserErrorCode.DATA_USER_GET_MAP_COLLECTIONS -> {
                    uiState.loadingBar = false
                    uiState.navCollectionMenu = true
                }
            }
        }
    }
}