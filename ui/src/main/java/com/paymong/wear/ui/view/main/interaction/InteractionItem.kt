package com.paymong.wear.ui.view.main.interaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view.feed.payPointFontSize

@Composable
fun InteractionButton(
    icon: Int,
    border: Int,
    onClick: () -> Unit
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(8.dp)
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = onClick
        ) {
            Image(
                alpha = 0.8f,
                painter = painterResource(R.drawable.interaction_bnt),
                contentDescription = null
            )
            Image(
                painter = painterResource(icon),
                contentDescription = null
            )
            Image(
                painter = painterResource(border),
                contentDescription = null
            )
        }
    }
}

@Composable
fun PayPointItem(
    payPoint: State<Int>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(30.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.pointbackground),
            contentDescription = null,
            modifier = Modifier
                .zIndex(0f)
        )
        Row(
            modifier = Modifier
                .matchParentSize()
                .zIndex(1f)
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.pointlogo),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.2f)
            )
            Text(
                text = "${payPoint.value}",
                fontSize = payPointFontSize.sp,
                color =  PaymongNavy,
                maxLines = 1,
                modifier = Modifier
                    .weight(0.8f)
            )
        }
    }
}