package com.paymong.wear.ui.view.charge.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R

@Composable
fun ChargePayPoint(
    price: Int,
    chargePoint: Int,
    isChargePayPointEnable: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .weight(0.7f)
        ) {
            Image(
                painter = painterResource(R.drawable.pointlogo),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .weight(0.5f)
            )
            Text(text = "X  $chargePoint", modifier = Modifier.weight(0.5f), fontSize = 20.sp)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            ChargeStarPointButton(
                width = 75,
                text = "$price",
                onClick = isChargePayPointEnable,
                modifier = Modifier
            )
        }
    }
}