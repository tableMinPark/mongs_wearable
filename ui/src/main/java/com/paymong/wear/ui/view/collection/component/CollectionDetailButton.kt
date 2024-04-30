package com.paymong.wear.ui.view.collection.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.theme.PaymongNavy


/** 목록 버튼 **/
@Composable
fun ListButton(
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(75.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
    ) {
        Image(
            painter = painterResource(R.drawable.blue_bnt),
            contentDescription = null,
            modifier = Modifier
                .zIndex(0f)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .zIndex(1f)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "목록",
                fontSize = 14.sp,
                color = PaymongNavy,
                maxLines = 1
            )
        }
    }
}

/** 배경 맵 선택 버튼 **/
@Composable
fun MapSelectButton(
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(75.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
    ) {
        Image(
            painter = painterResource(R.drawable.blue_bnt),
            contentDescription = null,
            modifier = Modifier
                .zIndex(0f)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .zIndex(1f)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "선택",
                fontSize = 14.sp,
                color = PaymongNavy,
                maxLines = 1
            )
        }
    }
}