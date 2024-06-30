package com.mongs.wear.ui.view.mainSlot.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite

@Composable
fun EmptyContent(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .zIndex(2f)
                        .fillMaxSize()
                ) {
                    BlueButton(
                        text = "슬롯 선택",
                        width = 90,
                        onClick = onClick
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp),
                        painter = painterResource(R.drawable.egg_blind),
                        contentDescription = null
                    )
                    Text(
                        text = "!",
                        textAlign = TextAlign.Center,
                        fontFamily = DAL_MU_RI,
                        fontWeight = FontWeight.Light,
                        fontSize = 25.sp,
                        color = PaymongWhite,
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun EmptyPreview() {
    Box {
        MainPagerBackground()
        EmptyContent()
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun EmptyLargePreview() {
    Box {
        MainPagerBackground()
        EmptyContent()
    }
}