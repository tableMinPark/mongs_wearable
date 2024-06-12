package com.mongs.wear.ui.viewModel.collectionMapPick

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
import javax.inject.Inject

@HiltViewModel
class CollectionMapPickViewModel @Inject constructor(
    private val getMapCollectionsUseCase: GetMapCollectionsUseCase,
    private val setBackgroundMapCodeUseCase: SetBackgroundMapCodeUseCase,
): ViewModel() {
    val uiState: UiState = UiState()
    val mapCollectionVoList: LiveData<List<MapCollectionVo>> get() = _mapCollectionVoList
    private val _mapCollectionVoList = MutableLiveData<List<MapCollectionVo>>()

    fun loadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                _mapCollectionVoList.postValue(getMapCollectionsUseCase())
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                uiState.navCollectionMenu = true
            }
        }
    }

    fun setBackground(mapCode: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                setBackgroundMapCodeUseCase(code = mapCode)
            } catch (_: UseCaseException) {
            } finally {
                uiState.detailDialog = false
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