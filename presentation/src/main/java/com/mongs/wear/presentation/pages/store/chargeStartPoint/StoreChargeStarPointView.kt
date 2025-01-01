package com.mongs.wear.presentation.pages.store.chargeStartPoint

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.presentation.common.manager.BillingManager
import com.mongs.wear.presentation.component.common.background.StoreNestedBackground
import com.mongs.wear.presentation.component.common.button.BlueButton
import com.mongs.wear.presentation.component.common.bar.LoadingBar

@Composable
fun StoreChargeStarPointView(
    navController: NavController,
    storeChargeStarPointViewModel: StoreChargeStarPointViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val productVoList = storeChargeStarPointViewModel.productVoList.observeAsState(ArrayList())

    Box {
        StoreNestedBackground()

        if (storeChargeStarPointViewModel.uiState.loadingBar) {
            StoreChargeStarPointLoadingBar()
        } else {
            PaymentChargeStarPointContent(
                productVoList = productVoList.value,
                consumeOrder = { productId ->
                    storeChargeStarPointViewModel.consumeOrder(
                        productId = productId
                    )
                },
                productOrder = { productId ->
                    storeChargeStarPointViewModel.productOrder(
                        activity = context as Activity,
                        productId = productId
                    )
                },
                modifier = Modifier.zIndex(1f)
            )
        }
    }

    LaunchedEffect(storeChargeStarPointViewModel.uiState.navStoreMenu) {
        if (storeChargeStarPointViewModel.uiState.navStoreMenu) {
            navController.popBackStack()
        }
    }
}

@Composable
private fun PaymentChargeStarPointContent(
    productVoList: List<BillingManager.ProductVo>,
    productOrder: (String) -> Unit,
    consumeOrder: (String) -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            for (productVo in productVoList) {
                Row {
                    if (productVo.hasNotConsumed) {
                        BlueButton(
                            text = "소비",
                            onClick = { consumeOrder(productVo.productId) },
                            width = 120
                        )
                    } else {
                        BlueButton(
                            text = "구매",
                            onClick = { productOrder(productVo.productId) },
                            width = 120
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StoreChargeStarPointLoadingBar(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        LoadingBar()
    }
}
