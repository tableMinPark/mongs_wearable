package com.mongs.wear.ui.global.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Shapes
import androidx.wear.compose.material.Typography
import com.mongs.wear.ui.R

val DAL_MU_RI = FontFamily(Font(R.font.dalmoori))

val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val PaymongBrown = Color(0xFFA79287)
val PaymongRed = Color(0xFFE90B0B)
val PaymongPink = Color(0xFFFF6B6B)
val PaymongPink200 = Color(0xFFFED9D9)
val PaymongGreen = Color(0xFF3BE368)
val PaymongGreen200 = Color(0xFFE0FFD1)
val PaymongYellow = Color(0xFFFFDA2D)
val PaymongYellow200 = Color(0xFFFFF7D2)
val PaymongBlue = Color(0xFF8DCEFE)
val PaymongBlue200 = Color(0xFFD3ECFF)
val PaymongPurple = Color(0xFFCCA2FE)
val PaymongNavy = Color(0xFF0C4DA2)
val PaymongWhite = Color(0xFFFFFFFF)
val PaymongLightGray = Color(0xFFF0F0F0)

@Composable
fun MongsTheme(content: @Composable () -> Unit) {
    val typography = Typography(defaultFontFamily = DAL_MU_RI)
    val shapes = Shapes(
        small = RoundedCornerShape(50.dp),
        medium = RoundedCornerShape(50.dp),
        large = RoundedCornerShape(50.dp)
    )

    MaterialTheme(
        typography = typography,
        shapes = shapes,
        content = content
    )
}