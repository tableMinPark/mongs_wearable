package com.paymong.wear.ui.view.main.slot

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import kotlinx.coroutines.delay

const val ripSize = 120
const val poopSize = 25
const val heartSize = 17

@Composable
fun Empty(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "캐릭터를 생성해주세요", modifier = Modifier.align(Alignment.Center).padding(bottom = 35.dp))

        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .size(40.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        )
    }
}

@Composable
fun Dead() {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(ripSize.dp),
            painter = painterResource(R.drawable.rip),
            contentDescription = null
        )
    }
}

@Composable
fun GraduationEffect(graduation: () -> Unit) {
    val delayList = listOf(100L, 500L, 500L, 500L, 500L, 400L)
    val totalStep = 4
    var step by remember { mutableIntStateOf(0) }
    val imageList = listOf(
        painterResource(R.drawable.star_1),
        painterResource(R.drawable.star_2),
        painterResource(R.drawable.star_3),
        painterResource(R.drawable.graduation)
    )

    LaunchedEffect(Unit) {
        for (time in delayList) {
            delay(time)
            step++
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { if (step > totalStep) graduation() },
    ) {
        if (step < totalStep) {
            Image(
                painter = imageList[step],
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .matchParentSize()
            )
        } else {
            Text(
                text = "축하합니다!\n" +
                        "졸업을 위해\n" +
                        "화면을 터치해주세요.",
                style = MaterialTheme.typography.display2,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(0.dp, (-50).dp),
            )
        }
    }
}

@Composable
fun EvolutionReady(evolutionStart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                evolutionStart()
            },
        contentAlignment = Alignment.Center) {
        Text(
            text = "진화를 위해\n" +
                    "화면을 터치해주세요.",
            style = MaterialTheme.typography.display2
        )
    }
}

@Composable
fun EvolutionEffect(evolutionEnd: () -> Unit) {
    val delayList = listOf(100L, 500L, 500L, 500L)
    val totalStep = 3
    var step by remember { mutableIntStateOf(0) }
    val imageList = listOf(
        painterResource(R.drawable.create_effect_1),
        painterResource(R.drawable.create_effect_2),
        painterResource(R.drawable.create_effect_3)
    )

    LaunchedEffect(Unit) {
        for (time in delayList) {
            if (step == totalStep) {
                evolutionEnd()
            }
            delay(time)
            step++
        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (step < totalStep) {
            Image(
                painter = imageList[step],
                contentDescription = null,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

@Composable
fun Poop(poopCount: Int) {
    val poopY = arrayOf(
        arrayOf(-40, -10),
        arrayOf(38, -12),
        arrayOf(-30, -2),
        arrayOf(27, 0),
    )
    val poop = painterResource(R.drawable.poops)
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        for (count in 1..poopCount) {
            val idx = count - 1
            Image(
                painter = poop,
                modifier = Modifier
                    .offset(poopY[idx][0].dp, poopY[idx][1].dp)
                    .size(poopSize.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Heart() {
    val heart = painterResource(R.drawable.heart)
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = heart,
            modifier = Modifier
                .size(heartSize.dp)
                .offset(0.dp, 25.dp),
            contentDescription = null
        )
    }
}
