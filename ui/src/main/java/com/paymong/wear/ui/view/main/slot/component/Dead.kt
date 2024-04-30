package com.paymong.wear.ui.view.main.slot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.wear.ui.R

@Composable
fun Dead(
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(R.drawable.rip),
            contentDescription = null
        )
    }
}
