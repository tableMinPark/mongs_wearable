package com.paymong.wear.ui.view.slotSelect.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.theme.PaymongNavy
import com.paymong.wear.ui.global.theme.PaymongWhite
import com.paymong.wear.ui.global.theme.PaymongYellow
import com.paymong.wear.ui.global.theme.PaymongYellow200


@Composable
fun SlotBuyButton(
    width: Int = 63,
    disable: Boolean = false,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(width.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Image(
            painter = painterResource( if (disable) R.drawable.gray_btn else R.drawable.yellow_btn),
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
//            Image(
//                painter = painterResource(R.drawable.starpoint_logo),
//                contentDescription = null,
//                modifier = Modifier
//                    .weight(0.2f)
//            )
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (disable) PaymongYellow else PaymongWhite,
                maxLines = 1,
//                modifier = Modifier.weight(0.8f)
            )
        }
    }
}