package com.paymong.wear.ui.view.training.jumping

import android.util.Log
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.paymong.wear.ui.global.component.Mong
import com.paymong.wear.ui.global.resource.MongResourceCode
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.viewModel.training.CharacterState
import com.paymong.wear.ui.viewModel.training.HurdleState
import com.paymong.wear.ui.viewModel.training.TrainingJumpingViewModel
import com.paymong.wear.ui.R

@Composable
fun TrainingJumpingContent(
    trainingJumpingViewModel: TrainingJumpingViewModel = hiltViewModel()
) {
    val bottomPadding = if (LocalConfiguration.current.screenWidthDp < 200) 55 else 65

    val score = trainingJumpingViewModel.score.observeAsState(0)
    val character = trainingJumpingViewModel.character.observeAsState(CharacterState())
    val hurdle = trainingJumpingViewModel.hurdle.observeAsState(HurdleState())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = { character.value.isJumping = true }
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding.dp)
                .border(width = 2.dp, color = Color.Red)
        ) {
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "${score.value}")
            }
            Row (
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Character(y = character.value.y, x = character.value.x)
                Log.d("test", "${score.value} : ${hurdle.value.hurdleList.size}")
                hurdle.value.hurdleList.forEach {
                    Hurdle(y = it.y, x = it.x)
                }
            }
        }
    }
}

@Composable
fun Character(
    y: Int,
    x: Int
){
    Box(
        modifier = Modifier
            .offset(y = y.dp, x = x.dp)
            .border(width = 1.dp, color = Color.Yellow)
    ) {
        Mong(
            mong = MongResourceCode.CH100,
            modifier = Modifier,
            ratio = 0.4f,
            isPng = true
        )
    }
}

@Composable
fun Hurdle(
    y: Int,
    x: Int
) {
    Box(
        modifier = Modifier
            .offset(y = y.dp, x = x.dp)
            .border(width = 1.dp, color = Color.Blue)
    ) {
        Image(
            painter = painterResource(R.drawable.poops),
            modifier = Modifier
                .size(25.dp),
            contentDescription = null
        )
    }
}