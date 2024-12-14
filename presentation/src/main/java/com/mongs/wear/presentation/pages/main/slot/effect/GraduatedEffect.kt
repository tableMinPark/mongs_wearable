package com.mongs.wear.presentation.pages.main.slot.effect

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.component.background.MainPagerBackground

@Composable
fun GraduatedEffect(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .padding(top = 25.dp)
                .size(35.dp),
            painter = painterResource(R.drawable.star_3),
            contentDescription = null
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun GraduationEndPreview() {
    Box {
        MainPagerBackground()
        GraduatedEffect()
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun GraduationEndLargePreview() {
    Box {
        MainPagerBackground()
        GraduatedEffect()
    }
}