package com.mongs.wear.presentation.pages.training.jumping

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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.domain.slot.vo.SlotVo
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.assets.MongResourceCode
import com.mongs.wear.presentation.assets.NavItem
import com.mongs.wear.presentation.component.background.TrainingNestedBackground
import com.mongs.wear.presentation.component.common.ScoreBox
import com.mongs.wear.presentation.component.dialog.training.TrainingEndDialog
import com.mongs.wear.presentation.component.dialog.training.TrainingStartDialog
import com.mongs.wear.presentation.pages.training.jumping.TrainingJumpingViewModel.HurdleEngine
import com.mongs.wear.presentation.pages.training.jumping.TrainingJumpingViewModel.PlayerEngine

@Composable
fun TrainingJumpingView(
    navController: NavController,
    trainingJumpingViewModel: TrainingJumpingViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    val slotVoState = trainingJumpingViewModel.slotVo.observeAsState()
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
            mongCode =  slotVoState.value?.mongTypeCode ?: SlotVo().mongTypeCode,
            jump = trainingJumpingViewModel::jump,
            playerEngine = playerEngine.value,
            hurdleEngines = hurdleEngines,
            modifier = Modifier.zIndex(1f),
        )
        TrainingJumpingInfoContent(
            score = trainingJumpingViewModel.score.intValue,
            modifier = Modifier.zIndex(2f),
        )

        if (trainingJumpingViewModel.uiState.trainingStartDialog) {
            TrainingStartDialog(
                firstText = "화면을 클릭하여",
                secondText = "장애물을 뛰어넘기!",
                rewardPayPoint = 2,
                trainingStart = {
                    trainingJumpingViewModel.trainingStart()
                    trainingJumpingViewModel.uiState.trainingStartDialog = false
                },
                modifier = Modifier.zIndex(3f),
            )
        } else if (trainingJumpingViewModel.uiState.trainingOverDialog) {
            TrainingEndDialog(
                trainingEnd = {
                    trainingJumpingViewModel.trainingEnd()
                },
                rewardPayPoint = trainingJumpingViewModel.score.intValue * 2,
                modifier = Modifier.zIndex(3f),
            )
        }
    }

    LaunchedEffect(trainingJumpingViewModel.uiState.navMainPager) {
        if (trainingJumpingViewModel.uiState.navMainPager) {
            navController.popBackStack(route = NavItem.TrainingJumping.route, inclusive = true)
            trainingJumpingViewModel.uiState.navMainPager = false
        }
    }

    LaunchedEffect(frame.value) {}
}

@Composable
private fun TrainingJumpingContent(
    mongCode: String,
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
                            mongCode = mongCode,
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
    score: Int,
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
    }
}

@Composable
private fun Player(
    mongCode: String,
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
            painter = painterResource(MongResourceCode.valueOf(mongCode).pngCode),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = -1f  // 좌우 반전을 위해 X축 스케일을 -1로 설정
                ),
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
            mongCode = "CH200",
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
            mongCode = "CH201",
            jump = {},
            playerEngine = PlayerEngine(),
            hurdleEngines = mutableListOf(),
            modifier = Modifier.zIndex(1f),
        )
    }
}

