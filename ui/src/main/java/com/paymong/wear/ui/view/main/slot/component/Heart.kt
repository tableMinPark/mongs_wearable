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
fun Heart(
    modifier: Modifier
) {
    val heart = painterResource(R.drawable.heart)
    Box(
        modifier = modifier
    ) {
        Image(
            painter = heart,
            contentDescription = null,
            modifier = Modifier
                .size(17.dp),
        )
    }
}
