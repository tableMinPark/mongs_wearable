package com.paymong.wear.ui.global.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Typography
import com.paymong.wear.ui.R

private val dalMooRi = FontFamily(Font(R.font.dalmoori))
private val samsungOneKorean = FontFamily(Font(R.font.samsungonekorean))

val SMALL = Typography(
    defaultFontFamily = dalMooRi,
    // MainLanding (로딩 화면)
    body1 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        lineHeight = 50.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    ),
    // BattleView (배틀)
    body2 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = PaymongPink200
    ),
    // Main (한 줄)
    display1 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    ),
    // Main (두 줄 이상 간격 중간)
    display2 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    ),
    // Main (두 줄 이상 간격 많음)
    display3 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        lineHeight = 50.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    )
)

val BIG = Typography(
    defaultFontFamily = dalMooRi,
    // MainLanding (로딩 화면)
    body1 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        lineHeight = 50.sp,
        fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    ),
    // BattleView (배틀)
    body2 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        color = PaymongPink200
    ),
    // Main (한 줄)
    display1 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    ),
    // Main (두 줄 이상 간격 중간)
    display2 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        lineHeight = 22.sp,
        fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    ),
    // Main (두 줄 이상 간격 많음)
    display3 = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = dalMooRi,
        lineHeight = 50.sp,
        fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        color = PaymongWhite
    )
)
