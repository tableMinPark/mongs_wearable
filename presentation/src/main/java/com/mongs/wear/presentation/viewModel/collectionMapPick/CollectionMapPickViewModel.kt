package com.mongs.wear.presentation.viewModel.collectionMapPick

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.collection.GetMapCollectionsUseCase
import com.mongs.wear.domain.usecase.configure.SetBackgroundMapCodeUseCase
import com.mongs.wear.domain.vo.MapCollectionVo
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
            } catch (_: UseCaseException) {
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
            } catch (_: UseCaseException) {
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