package com.paymong.wear.ui.view.main.slot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import kotlinx.coroutines.delay

@Composable
fun GraduationEffect(
    isGraduationCheck: Boolean,
    isPageChange: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
    graduationCheck: () -> Unit,
) {
    if (!isGraduationCheck) {
        var effectEnd by remember { mutableStateOf(false) }
        val delayList = listOf(300L, 400L, 500L, 1000L)
        var nowStep by remember { mutableIntStateOf(0) }
        val imageList = listOf(
            painterResource(R.drawable.star_1),
            painterResource(R.drawable.star_2),
            painterResource(R.drawable.star_3),
            painterResource(R.drawable.graduation)
        )

        LaunchedEffect(Unit) {
            for (step in 0..3) {
                nowStep = step
                delay(delayList[step])
            }
            effectEnd = true
            graduationCheck()
        }

        if (!effectEnd) {
            Box(
                modifier = modifier
                    .zIndex(2.1f)
            ) {
                Image(
                    painter = imageList[nowStep],
                    contentDescription = null,
                )
            }
        }
    } else if (!isPageChange) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp)
                .zIndex(2.2f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {
            Text(
                text = "졸업 (학사모)"
            )
        }
    }

//    else if(!isPageChange) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .zIndex(2.2f)
//                .background(color = Color.Black.copy(alpha = 0.4f))
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null,
//                    onClick = onClick
//                )
//        ) {
//            Text(
//                text = "축하합니다!\n졸업을 위해\n화면을 터치해주세요!"
//            )
//        }
//    }
}