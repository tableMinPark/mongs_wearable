package com.mongs.wear.presentation.pages.collection.mong

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.collection.usecase.GetMongCollectionsUseCase
import com.mongs.wear.domain.collection.vo.MongCollectionVo
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionMongPickViewModel @Inject constructor(
    private val getMongCollectionsUseCase: GetMongCollectionsUseCase,
): BaseViewModel() {

    val mongCollectionVoList: LiveData<List<MongCollectionVo>> get() = _mongCollectionVoList
    private val _mongCollectionVoList = MutableLiveData<List<MongCollectionVo>>()

    init {
        viewModelScope.launch (Dispatchers.Main) {

            uiState.loadingBar = true

            _mongCollectionVoList.postValue(withContext(Dispatchers.IO) {
                getMongCollectionsUseCase()
            })

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
                UserErrorCode.DATA_USER_GET_MONG_COLLECTIONS -> {
                    uiState.loadingBar = false
                    uiState.navCollectionMenu = true
                }
            }
        }
    }
}