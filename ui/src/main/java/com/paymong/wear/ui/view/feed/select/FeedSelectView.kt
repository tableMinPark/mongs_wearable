package com.paymong.wear.ui.view.feed.select

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
import com.paymong.wear.domain.processCode.FeedSelectProcessCode
import com.paymong.wear.ui.viewModel.feed.FeedSelectViewModel
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.component.FeedSelectBackground
import kotlinx.coroutines.delay

@Composable
fun FeedSelectView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    code: String,
    feedSelectViewModel: FeedSelectViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    /** Process Code **/
    val processCode = feedSelectViewModel.processCode.observeAsState(FeedSelectProcessCode.STAND_BY)
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(true) }
    val isFeedItemListLoad = remember{ mutableStateOf(false) }
    /** Observe **/
    val slotVo = feedSelectViewModel.slotVo.observeAsState(DefaultValue.SLOT_VO)
    val feedItemList = feedSelectViewModel.feedItemList.observeAsState(ArrayList())

    when (processCode.value) {
        FeedSelectProcessCode.FEED_FAIL -> {
            Toast.makeText(
                context,
                FeedSelectProcessCode.FEED_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            feedSelectViewModel.resetProcessCode()
        }
        FeedSelectProcessCode.LOAD_FEED_ITEM_LIST_FAIL -> {
            Toast.makeText(
                context,
                FeedSelectProcessCode.LOAD_FEED_ITEM_LIST_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        }
        FeedSelectProcessCode.LOAD_FEED_ITEM_LIST_END -> {
            LaunchedEffect(Unit) {
                delay(DefaultValue.LOAD_DELAY)
                isLoadingBarShow.value = false
                isFeedItemListLoad.value = true
                feedSelectViewModel.resetProcessCode()
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
            FeedSelectBackground()
        }

        FeedSelectContent(
            slotVo = slotVo,
            feedItemList = feedItemList,
            isLoadingBarShow = isLoadingBarShow,
            isFeedItemListLoad = isFeedItemListLoad,
            feed = { feedCode ->
                scrollPage(1)
                feedSelectViewModel.feed(feedCode)
                navController.popBackStack(route = NavItem.FeedNested.route, inclusive = true)
            },
            cantFeed = {
                Toast.makeText(
                    context,
                    FeedSelectProcessCode.CANT_BUY_FEED_ITEM.message,
                    Toast.LENGTH_SHORT
                ).show()
            },
        )
    }

    LaunchedEffect(Unit) {
        isLoadingBarShow.value = true
        isFeedItemListLoad.value = false
        feedSelectViewModel.loadFeedItemList(code)
    }
}

