package com.mongs.wear.presentation.component.common.textbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.assets.DAL_MU_RI
import com.mongs.wear.presentation.assets.MongsNavy

@Composable
fun PayPoint(
    height: Int = 30,
    width: Int = 80,
    payPoint: Int = 0,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(height.dp)
            .width(width.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.pointbackground),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .zIndex(1.1f),
            contentScale = ContentScale.FillBounds,
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.zIndex(1.2f).padding(start = 10.dp, end = 10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(0.2f),
            ) {
                Image(
                    painter = painterResource(R.drawable.pointlogo),
                    contentDescription = null,
                    modifier = Modifier
                        .height(12.dp)
                        .width(12.dp),
                    contentScale = ContentScale.FillBounds,
                )
            }

            Text(
                text = "$payPoint",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = MongsNavy,
                maxLines = 1,
                modifier = Modifier.weight(0.8f)
            )
        }
    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun PayPointPreview() {
    PayPoint()
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun LongPayPointPreview() {
    PayPoint(width = 120)
}