package com.paymong.wear.ui.global.component.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.button.BlueButton

@Composable
fun Logo(
    height: Int = 80,
    width: Int = 100,
) {
    val logo = painterResource(R.drawable.watch_logo)

    Image(
        painter = logo,
        contentDescription = null,
        modifier = Modifier
            .height(height.dp)
            .width(width.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun LogoPreview() {
    Logo()
}
