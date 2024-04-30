package com.paymong.wear.ui.global.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.paymong.wear.ui.R

@Composable
fun LoadingBar(size: Int) {
    /** gif 이미지 로더 **/
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(ImageDecoderDecoder.Factory()) }
        .build()
    /** 로딩 이미지 **/
    val loading = R.drawable.loading

    Box {
        Image(
            painter = rememberAsyncImagePainter(
                model = loading,
                imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = Modifier.size(size.dp)
        )
    }
}