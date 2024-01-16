package com.paymong.wear.ui.view.initLanding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.paymong.wear.ui.R

@Composable
fun Logo(logoSize: Int) {
    val logo = painterResource(R.drawable.watch_logo)

    Image(
        painter = logo,
        contentDescription = null,
        modifier = Modifier.size(logoSize.dp)
    )
}

@Composable
fun Login(
    loginSize: Int,
    onClick: () -> Unit
) {
    val loginImage = painterResource(R.drawable.google_login)
    Image(
        painter = loginImage,
        contentDescription = null,
        modifier = Modifier
            .width(width = loginSize.dp)
            .padding(bottom = 25.dp)
            .clickable {
                onClick()
            }
    )
}