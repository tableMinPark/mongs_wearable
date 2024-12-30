package com.mongs.wear.presentation.pages.store.chargeStartPoint

import android.app.Activity
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
import com.mongs.wear.presentation.component.background.PaymentNestedBackground
import com.mongs.wear.presentation.component.button.BlueButton

@Composable
fun StoreChargeStarPointView(
    navController: NavController,
    storeChargeStarPointViewModel: StoreChargeStarPointViewModel = hiltViewModel(),
    activity: Activity = LocalContext.current as Activity
) {

    val productVoList = storeChargeStarPointViewModel.productVoList.observeAsState(ArrayList())

    Box {
        PaymentNestedBackground()
        PaymentChargeStarPointContent(
            productVoList = productVoList.value,
            productOrder = { productId ->
                storeChargeStarPointViewModel.productOrder(
                    activity = activity,
                    productId = productId
                )
            },
            modifier = Modifier.zIndex(1f)
        )
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
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            for (productVo in productVoList) {
                Row {
                    BlueButton(
                        text = productVo.productId,
                        onClick = { productOrder(productVo.productId) },
                        width = 120
                    )
                }
            }
        }
    }
}