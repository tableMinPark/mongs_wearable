package com.paymong.wear.ui.view.mainSlot.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.paymong.wear.ui.global.component.background.MainPagerBackground
import com.paymong.wear.ui.global.theme.DAL_MU_RI
import com.paymong.wear.ui.global.theme.PaymongWhite

@Composable
fun DeleteContent(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier.padding(bottom = 25.dp).size(130.dp),
                painter = painterResource(R.drawable.rip),
                contentDescription = null
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .zIndex(2f)
                .background(color = Color.Black.copy(alpha = 0.6f))
                .fillMaxSize()
        ) {
            Text(
                text = "삭제되었습니다\n\n슬롯을 변경해주세요",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = PaymongWhite,
            )
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun DeletePreview() {
    MainPagerBackground()
    DeleteContent()
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun DeleteLargePreview() {
    MainPagerBackground()
    DeleteContent()
}