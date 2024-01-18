package com.paymong.wear.ui.view_.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.wear.ui.R

@Composable
fun Logo(size: Int) {
    val logo = painterResource(R.drawable.watch_logo)

    Image(
        painter = logo,
        contentDescription = "Logo",
        modifier = Modifier.size(size.dp)
    )
}