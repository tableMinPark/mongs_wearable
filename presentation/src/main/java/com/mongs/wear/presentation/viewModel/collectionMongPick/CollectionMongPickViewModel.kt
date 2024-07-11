package com.mongs.wear.presentation.viewModel.collectionMongPick

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.collection.GetMongCollectionsUseCase
import com.mongs.wear.domain.vo.MongCollectionVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionMongPickViewModel @Inject constructor(
    private val getMongCollectionsUseCase: GetMongCollectionsUseCase,
): ViewModel() {
    val uiState: UiState = UiState()
    val mongCollectionVoList: LiveData<List<MongCollectionVo>> get() = _mongCollectionVoList
    private val _mongCollectionVoList = MutableLiveData<List<MongCollectionVo>>()

    init {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                uiState.loadingBar = true
                val mongCollectionVoList = withContext(Dispatchers.IO) {
                    getMongCollectionsUseCase()
                }
                _mongCollectionVoList.postValue(mongCollectionVoList)
                uiState.loadingBar = false
            } catch (_: UseCaseException) {
                uiState.navCollectionMenu = true
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