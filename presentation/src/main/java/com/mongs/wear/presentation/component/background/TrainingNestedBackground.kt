package com.mongs.wear.presentation.component.background

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.mongs.wear.presentation.R

@Composable
fun TrainingNestedBackground(
    isMoving: Boolean = false,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    if (!isMoving) {
        val mapResourceCode = R.drawable.training_bg

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(mapResourceCode),
                contentDescription = "TrainingNestedBackground",
                contentScale = ContentScale.Crop
            )
        }
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "TrainingNestedBackgroundTransition")
        val offsetX by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "TrainingNestedBackgroundOffset"
        )

        val background1 = ImageBitmap.imageResource(R.drawable.training_bg)
        val background2 = ImageBitmap.imageResource(R.drawable.training_bg)

        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val imageWidth = background1.width.toFloat()
                val imageHeight = background1.height.toFloat()
                val scaleFactor = canvasHeight / imageHeight
                val scaledWidth = imageWidth * scaleFactor
                val xOffset = -scaledWidth * offsetX

                fun DrawScope.drawBackground(image: ImageBitmap, xOffset: Float) {
                    drawIntoCanvas { canvas ->
                        val paint = android.graphics.Paint()
                        val rect = android.graphics.RectF(xOffset, 0f, xOffset + scaledWidth, canvasHeight)
                        canvas.nativeCanvas.drawBitmap(
                            image.asAndroidBitmap(),
                            null,
                            rect,
                            paint
                        )
                    }
                }

                drawBackground(background1, xOffset)
                drawBackground(background2, xOffset + scaledWidth)

                if (xOffset + scaledWidth < canvasWidth) {
                    drawBackground(background1, xOffset + 2 * scaledWidth)
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun TrainingNestedBackgroundPreview() {
    TrainingNestedBackground()
}