package com.paymong.wear.ui.view.common.background

import androidx.compose.foundation.Image
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
fun Process(processSize: Int) {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components { add(ImageDecoderDecoder.Factory()) }.build()
    val processImage = R.drawable.loading

    Image(
        painter = rememberAsyncImagePainter(model = processImage, imageLoader = imageLoader),
        contentDescription = null,
        modifier = Modifier
            .size(processSize.dp)
    )
}