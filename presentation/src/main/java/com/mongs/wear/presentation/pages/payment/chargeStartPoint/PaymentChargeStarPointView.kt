package com.mongs.wear.presentation.pages.payment.chargeStartPoint

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.presentation.component.background.PaymentNestedBackground

@Composable
fun PaymentChargeStarPointView(
    navController: NavController,
    paymentChargeStarPointViewModel: PaymentChargeStarPointViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    Box {
        PaymentNestedBackground()
        PaymentChargeStarPointContent(modifier = Modifier.zIndex(1f))
    }
}

@Composable
private fun PaymentChargeStarPointContent(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun PaymentChargeStarPointViewPreview() {
    Box {
        PaymentNestedBackground()
        PaymentChargeStarPointContent(
            modifier = Modifier.zIndex(1f)
        )
    }
}