package com.paymong.wear.ui.global.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.theme.PaymongNavy

@Composable
fun PayPoint(
    width: Int = 90,
    payPoint: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(width.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.pointbackground),
            contentDescription = null,
            modifier = Modifier
                .zIndex(1.1f)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .zIndex(1.2f)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.pointlogo),
                contentDescription = null,
                modifier = Modifier.weight(0.2f)
            )
            Text(
                text = "$payPoint",
                fontSize = 14.sp,
                color = PaymongNavy,
                maxLines = 1,
                modifier = Modifier.weight(0.8f)
            )
        }
    }
}