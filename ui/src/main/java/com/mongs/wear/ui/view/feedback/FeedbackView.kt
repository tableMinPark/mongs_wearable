package com.mongs.wear.ui.view.feedback

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.FeedbackBackground
import com.mongs.wear.ui.global.component.common.Chip
import com.mongs.wear.ui.global.component.common.ChipWithSecondLabel
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.dialog.ConfirmDialog
import com.mongs.wear.ui.global.dialog.OkDialog
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite
import com.mongs.wear.ui.viewModel.feedback.FeedbackViewModel

@Composable
fun FeedbackView(
    feedbackViewModel: FeedbackViewModel = hiltViewModel()
) {
    val feedbackCode = remember { mutableStateOf(FeedbackCode.COMMON) }

    Box {
        if (feedbackViewModel.uiState.loadingBar) {
            FeedbackBackground()
            FeedbackLoadingBar()
        } else if (feedbackViewModel.uiState.addDialog) {
            ConfirmDialog(
                text = "\"${feedbackCode.value.message}\" 오류를\n전송 하시겠습니까?",
                confirm = {
                    feedbackViewModel.addFeedback(feedbackCode = feedbackCode.value)
                },
                cancel = {
                    feedbackViewModel.uiState.addDialog = false
                }
            )
        } else if (feedbackViewModel.uiState.okDialog) {
            OkDialog(
                text = "신고되었습니다.\n처리결과는 메일로\n전달드리겠습니다.",
                confirm = { feedbackViewModel.uiState.okDialog = false }
            )
        } else {
            FeedbackBackground()
            FeedbackContent(
                feedbackDialog = { selectCode ->
                    feedbackCode.value = selectCode
                    feedbackViewModel.uiState.addDialog = true
                },
                modifier = Modifier.zIndex(1f)
            )
        }
    }
}

@Composable
private fun FeedbackLoadingBar(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        LoadingBar()
    }
}

@Composable
private fun FeedbackContent(
    feedbackDialog: (FeedbackCode) -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 1)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        PositionIndicator(scalingLazyListState = listState)
        ScalingLazyColumn(
            contentPadding = PaddingValues(vertical = 60.dp, horizontal = 6.dp),
            modifier = Modifier.fillMaxSize(),
            state = listState,
            autoCentering = null,
        ) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(
                        text = "피드백 종류 선택",
                        textAlign = TextAlign.Center,
                        fontFamily = DAL_MU_RI,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        color = PaymongWhite,
                        maxLines = 1,
                    )
                }
            }

            for (feedbackCode in FeedbackCode.values()) {
                item {
                    ChipWithSecondLabel(
                        icon = R.drawable.feedback,
                        border = R.drawable.interaction_bnt_darkpurple,
                        fontColor = Color.White,
                        backgroundColor = Color.Black,
                        label = feedbackCode.message,
                        secondaryLabel = feedbackCode.secondaryMessage,
                        onClick = { feedbackDialog(feedbackCode) },
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedbackViewPreview() {
    Box {
        FeedbackBackground()
        FeedbackContent(
            feedbackDialog = {},
            modifier = Modifier.zIndex(1f)
        )
    }
}