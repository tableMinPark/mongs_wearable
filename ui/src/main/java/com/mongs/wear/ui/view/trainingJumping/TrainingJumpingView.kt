package com.mongs.wear.ui.view.trainingJumping

import android.content.Context
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.TrainingNestedBackground
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.component.common.ScoreBox
import com.mongs.wear.ui.viewModel.trainingJumping.TrainingJumpingViewModel
import com.mongs.wear.ui.viewModel.trainingJumping.TrainingJumpingViewModel.HurdleEngine
import com.mongs.wear.ui.viewModel.trainingJumping.TrainingJumpingViewModel.PlayerEngine

@Composable
fun TrainingJumpingView(
    navController: NavController,
    trainingJumpingViewModel: TrainingJumpingViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    val frame = trainingJumpingViewModel.frame.observeAsState(0)
    val playerEngine = trainingJumpingViewModel.playerEngine
    val hurdleEngines = trainingJumpingViewModel.hurdleEngines

    DisposableEffect(Unit) {
        val window = (context as ComponentActivity).window
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    Box {
        TrainingNestedBackground(
            isMoving = !trainingJumpingViewModel.uiState.isTrainingOver
        )
        TrainingJumpingContent(
            jump = trainingJumpingViewModel::jump,
            playerEngine = playerEngine.value,
            hurdleEngines = hurdleEngines,
            modifier = Modifier.zIndex(1f),
        )
        TrainingJumpingInfoContent(
            trainingStart = trainingJumpingViewModel::trainingStart,
            score = trainingJumpingViewModel.score.intValue,
            isTrainingOver = trainingJumpingViewModel.uiState.isTrainingOver,
            modifier = Modifier.zIndex(2f),
        )
    }

    LaunchedEffect(frame.value) {}
}

@Composable
private fun TrainingJumpingContent(
    jump: () -> Unit,
    playerEngine: PlayerEngine,
    hurdleEngines: List<HurdleEngine>,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = jump,
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.72f)
            ) {
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.zIndex(1f)
                    ) {
                        for ((index, hurdleEngine) in hurdleEngines.withIndex()) {
                            Hurdle(
                                image = R.drawable.poops,
                                height = hurdleEngine.height,
                                width = hurdleEngine.width,
                                modifier = Modifier
                                    .zIndex(-index.toFloat())
                                    .offset(y = (hurdleEngine.py).dp, x = (hurdleEngine.px).dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.zIndex(2f)
                    ) {
                        Player(
                            height = playerEngine.height,
                            width = playerEngine.width,
                            modifier = Modifier
                                .zIndex(1f)
                                .offset(y = (playerEngine.py).dp, x = (playerEngine.px).dp)
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.28f)
            ) {
                Spacer(modifier = Modifier)
            }
        }
    }
}

@Composable
private fun TrainingJumpingInfoContent(
    trainingStart: () -> Unit,
    score: Int,
    isTrainingOver: Boolean,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 15.dp, bottom = 15.dp)
    ) {
        ScoreBox(
            score = score,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        if (isTrainingOver) {
            BlueButton(
                text = "시작",
                onClick = trainingStart,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun Player(
    height: Int,
    width: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(height.dp)
            .width(width.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ch100),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Composable
private fun Hurdle(
    image: Int,
    height: Int,
    width: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(height.dp)
            .width(width.dp),
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedMenuViewPreview() {
    Box {
        TrainingNestedBackground()
        TrainingJumpingContent(
            jump = {},
            playerEngine = PlayerEngine(),
            hurdleEngines = mutableListOf(),
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedMenuViewPreview() {
    Box {
        TrainingNestedBackground()
        TrainingJumpingContent(
            jump = {},
            playerEngine = PlayerEngine(),
            hurdleEngines = mutableListOf(),
            modifier = Modifier.zIndex(1f),
        )
    }
}

