package com.paymong.wear.ui.global.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.theme.DAL_MU_RI
import com.paymong.wear.ui.global.theme.PaymongNavy


@Composable
fun BlueButton(
    text: String,
    onClick: () -> Unit,
    height: Int = 30,
    width: Int = 63,
    disable: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(height.dp)
            .width(width.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        val buttonImage = if (disable) R.drawable.gray_btn else R.drawable.blue_bnt

        Image(
            painter = painterResource(buttonImage),
            contentDescription = null,
            modifier = Modifier.zIndex(1.1f),
            contentScale = ContentScale.FillBounds
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.zIndex(1.2f).padding(start = 10.dp, end = 10.dp),
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = PaymongNavy,
                maxLines = 1,
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun LongBlueButtonPreview() {
    BlueButton(text = "확인했습니다", onClick = {}, width = 100)
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun EnableBlueButtonPreview() {
    BlueButton(text = "확인", onClick = {})
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun DisableBlueButtonPreview() {
    BlueButton(text = "확인", onClick = {}, disable = true)
}