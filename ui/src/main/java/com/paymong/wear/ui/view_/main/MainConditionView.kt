package com.paymong.wear.ui.view_.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.theme.PaymongBlue
import com.paymong.wear.ui.theme.PaymongGreen
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.theme.PaymongPink
import com.paymong.wear.ui.theme.PaymongYellow

@Composable
fun MainConditionView() {

    Box {
        MainConditionViewContent()
    }
}

@Composable
fun MainConditionViewContent() {
    Box(
        modifier = Modifier
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PayPoint(payPoint = 9999)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Condition(
                    icon = R.drawable.health,
                    progress = 0f,
                    indicatorColor = PaymongPink
                )
                Condition(
                    icon = R.drawable.satiety,
                    progress = 0f,
                    indicatorColor = PaymongYellow
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Condition(
                    icon = R.drawable.strength,
                    progress = 0f,
                    indicatorColor = PaymongGreen
                )
                Condition(
                    icon = R.drawable.sleep,
                    progress = 0f,
                    indicatorColor = PaymongBlue
                )
            }
        }
    }
}

@Composable
fun PayPoint(
    payPoint: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(90.dp)
            .padding(bottom = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.pointbackground),
            contentDescription = null,
            modifier = Modifier
                .zIndex(1.1f)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .zIndex(1.2f)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.pointlogo),
                contentDescription = null,
                modifier = Modifier.weight(0.2f)
            )
            Text(
                text = "$payPoint",
                fontSize = 14.sp,
                color = PaymongNavy,
                maxLines = 1,
                modifier = Modifier.weight(0.8f)
            )
        }
    }
}

const val imageSize = 25
const val circularSize = 60
const val strokeWidth = 4
@Composable
fun Condition(
    icon: Int,
    progress: Float,
    indicatorColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(6.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(imageSize.dp)
        )
        CircularProgressIndicator(
            modifier = Modifier.size(circularSize.dp),
            startAngle = 271f,
            endAngle = 270f,
            progress = progress,
            strokeWidth = strokeWidth.dp,
            indicatorColor = indicatorColor,
        )
    }
}
