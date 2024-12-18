package com.mongs.wear.presentation.pages.collection.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.collection.usecase.GetMapCollectionsUseCase
import com.mongs.wear.domain.common.usecase.SetBackgroundMapCodeUseCase
import com.mongs.wear.domain.collection.vo.MapCollectionVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionMapPickViewModel @Inject constructor(
    private val getMapCollectionsUseCase: GetMapCollectionsUseCase,
    private val setBackgroundMapCodeUseCase: SetBackgroundMapCodeUseCase,
): ViewModel() {

    val uiState: UiState = UiState()
    val mapCollectionVoList: LiveData<List<MapCollectionVo>> get() = _mapCollectionVoList
    private val _mapCollectionVoList = MutableLiveData<List<MapCollectionVo>>()

    init {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                uiState.loadingBar = true
                val mapCollectionVoList = withContext(Dispatchers.IO) {
                    getMapCollectionsUseCase()
                }
                _mapCollectionVoList.postValue(mapCollectionVoList)
                uiState.loadingBar = false
            } catch (_: ErrorException) {
                uiState.navCollectionMenu = true
                uiState.loadingBar = false
            }
        }
    }

    fun setBackground(mapCode: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.loadingBar = true
                setBackgroundMapCodeUseCase(code = mapCode)
                uiState.detailDialog = false
                uiState.loadingBar = false
            } catch (_: ErrorException) {
                uiState.detailDialog = false
                uiState.loadingBar = false
            }
        }
    }

    class UiState (
        navCollectionMenu: Boolean = false,
        loadingBar: Boolean = true,
        detailDialog: Boolean = false,
    ) {
        var navCollectionMenu by mutableStateOf(navCollectionMenu)
        var loadingBar by mutableStateOf(loadingBar)
        var detailDialog by mutableStateOf(detailDialog)
    }
}