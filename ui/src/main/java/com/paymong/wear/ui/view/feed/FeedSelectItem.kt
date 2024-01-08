package com.paymong.wear.ui.view.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.FeedCode
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view.common.background.Process

const val payPointFontSize = 13
const val priceFontSize = 13
const val feedNameFontSize = 13
const val feedImageSize = 50

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

@Composable
fun FeedItem(
    name: String,
    code: String
) {
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            Text(text = name, fontSize = feedNameFontSize.sp)
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
        ) {
            Image(
                painter = painterResource(FeedCode.valueOf(code).code),
                contentDescription = null,
                modifier = Modifier.size(feedImageSize.dp)
            )
        }
    }
}

@Composable
fun FeedSelectEnableButton(
    onClick: (String) -> Unit,
    code: String,
    price: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(35.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick(code) },
            )
    ) {
        Image(
            painter = painterResource(R.drawable.blue_bnt),
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
                text = "$price",
                color = PaymongNavy,
                fontSize = priceFontSize.sp,
                maxLines = 1,
                modifier = Modifier
                    .weight(0.8f)
            )
        }
    }
}

@Composable
fun FeedSelectDisableButton(
    onClick: () -> Unit,
    price: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(35.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
    ) {
        Image(
            painter = painterResource(R.drawable.gray_btn),
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
                text = "$price",
                color = PaymongNavy,
                fontSize = priceFontSize.sp,
                maxLines = 1,
                modifier = Modifier
                    .weight(0.8f)
            )
        }
    }
}

@Composable
fun FeedSelectProcess() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Process(processSize = 30)
    }
}