package com.mongs.wear.presentation.pages.store.chargeStartPoint

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.player.usecase.GetProductIdsUseCase
import com.mongs.wear.presentation.common.manager.BillingManager
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreChargeStarPointViewModel @Inject constructor(
    private val billingManager: BillingManager,
    private val getProductIdsUseCase: GetProductIdsUseCase,
): BaseViewModel() {

    private val _productVoList = MediatorLiveData<List<BillingManager.ProductVo>>(ArrayList())
    val productVoList: LiveData<List<BillingManager.ProductVo>> get() = _productVoList


    init {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {

            uiState.loadingBar = true

            billingManager.connect()

            val productIds = getProductIdsUseCase()
            _productVoList.postValue(billingManager.getProductList(productIds))

            uiState.loadingBar = false
        }
    }

    fun productOrder(activity: Activity, productId: String) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            billingManager.payProduct(activity = activity, productId = productId)
        }
    }


    val uiState: UiState = UiState()

    class UiState : BaseUiState() {

        var loadingBar by mutableStateOf(false)

        var navStoreMenu by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {

                UserErrorCode.DATA_USER_GET_PRODUCT_IDS -> {
                    uiState.loadingBar = false
                }

                UserErrorCode.PRESENTATION_USER_BILLING_CONNECT,
                UserErrorCode.PRESENTATION_USER_BILLING_NOT_SUPPORT -> {
                    uiState.loadingBar = false
                    uiState.navStoreMenu = true
                }
            }
        }
    }
}