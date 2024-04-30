package com.paymong.wear.ui.view.feed.select.component

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

@Composable
fun FeedButton(
    disable: Boolean,
    price: Int,
    onClick: () -> Unit,
    modifier: Modifier
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
            painter = painterResource( if(disable) R.drawable.gray_btn else R.drawable.blue_bnt ),
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
                text = "$price",
                fontSize = 15.sp,
                color = PaymongNavy,
                maxLines = 1,
                modifier = Modifier.weight(0.8f)
            )
        }
    }
}