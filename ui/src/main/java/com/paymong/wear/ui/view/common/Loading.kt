package com.paymong.wear.ui.view.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R

const val logoSize = 120

@Composable
fun Logo() {
    val logo = painterResource(R.drawable.watch_logo)

    Image(
        painter = logo,
        contentDescription = null,
        modifier = Modifier.size(logoSize.dp)
    )
}

@Composable
fun FailOrError(message : String) {
    Text(
        text = message,
        style = MaterialTheme.typography.body1
    )
}