package com.paymong.wear.ui.view.feedback

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.processCode.FeedbackProcessCode
import com.paymong.wear.ui.viewModel.feedback.FeedbackViewModel
import com.paymong.wear.ui.global.component.FeedbackBackground
import kotlinx.coroutines.delay

@Composable
fun FeedbackView(
    navController: NavController,
    feedbackViewModel: FeedbackViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    /** Process Code **/
    val processCode = feedbackViewModel.processCode.observeAsState(FeedbackProcessCode.STAND_BY)
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(true) }
    val isFeedItemListLoad = remember{ mutableStateOf(false) }
    /** Observe **/
    val feedbackItemMap = feedbackViewModel.feedbackItemMap.observeAsState(HashMap())

    when (processCode.value) {
        FeedbackProcessCode.FEEDBACK_FAIL -> {
            Toast.makeText(
                context,
                FeedbackProcessCode.FEEDBACK_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            feedbackViewModel.resetProcessCode()
        }
        FeedbackProcessCode.LOAD_FEEDBACK_ITEM_LIST_FAIL -> {
            Toast.makeText(
                context,
                FeedbackProcessCode.LOAD_FEEDBACK_ITEM_LIST_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        }
        FeedbackProcessCode.LOAD_FEEDBACK_ITEM_LIST_END -> {
            LaunchedEffect(Unit) {
                delay(DefaultValue.LOAD_DELAY)
                isLoadingBarShow.value = false
                isFeedItemListLoad.value = true
                feedbackViewModel.resetProcessCode()
            }
        }
        else -> {}
    }

    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            FeedbackBackground()
        }

        FeedbackContent(
            feedbackItemMap = feedbackItemMap,
            feedback = { code, groupCode, message -> feedbackViewModel.feedback(
                code = code,
                groupCode = groupCode,
                message = message
            )}
        )
    }

    LaunchedEffect(Unit) {
        isLoadingBarShow.value = true
        isFeedItemListLoad.value = false
        feedbackViewModel.loadFeedbackItemList()
    }
}