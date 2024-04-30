package com.paymong.wear.ui.view.feedback

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import com.paymong.wear.domain.repository.common.vo.FeedbackCodeVo
import com.paymong.wear.ui.global.dialog.ConfirmDialog
import com.paymong.wear.ui.view.feedback.component.Feedback


@Composable
fun FeedbackContent(
    feedbackItemMap: State<Map<String, List<FeedbackCodeVo>>>,
    feedback: (String, String, String) -> Unit
) {
    val isFeedbackConfirmDialogShow = remember { mutableStateOf(false) }
    /** 리스트 **/
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        PositionIndicator(scalingLazyListState = listState)
        ScalingLazyColumn(
            contentPadding = PaddingValues(vertical = 60.dp, horizontal = 6.dp),
            modifier = Modifier.fillMaxSize(),
            state = listState,
            autoCentering = null,
        ) {
            item {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "피드백"
                )
            }
            
            feedbackItemMap.value.keys.forEach { groupCode ->
                feedbackItemMap.value[groupCode]?.forEach { feedbackItem ->
                    item {
                        Feedback(
                            label = feedbackItem.message,
                            onClick = { feedback(feedbackItem.code, groupCode, feedbackItem.message) }
                        )
                    }
                }
            }
        }
    }
//
//    if (isFeedbackConfirmDialogShow.value) {
//        ConfirmDialog(
//            text = "오류 신고 하시겠습니까?",
//            confirm = ,
//            cancel = {}
//        )
//    }
}