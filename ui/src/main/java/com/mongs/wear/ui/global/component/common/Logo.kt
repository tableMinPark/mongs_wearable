package com.mongs.wear.ui.global.component.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mongs.wear.ui.R

@Composable
fun Logo(
    isOpen: Boolean = true,
) {
    if (isOpen) {
        Image(
            painter = painterResource(R.drawable.logo_open),
            contentDescription = null,
            modifier = Modifier
                .height(90.dp)
                .width(75.dp),
            contentScale = ContentScale.FillBounds
        )
    } else {
        Image(
            painter = painterResource(R.drawable.logo_not_open),
            contentDescription = null,
            modifier = Modifier
                .height(95.dp)
                .width(80.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun LogoPreview() {
    Logo(isOpen = false)
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun OpenLogoPreview() {
    Logo(isOpen = true)
}
