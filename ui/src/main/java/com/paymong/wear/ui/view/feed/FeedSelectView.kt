package com.paymong.wear.ui.view.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import com.paymong.wear.domain.model.FeedModel
import com.paymong.wear.domain.viewModel.code.FeedSelectCode
import com.paymong.wear.domain.viewModel.feed.FeedSelectViewModel
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view.common.background.FeedSelectBackGround
import kotlin.math.max
import kotlin.math.min

@Composable
fun FeedSelectView(
    navController: NavController,
    code: String,
    feedSelectViewModel: FeedSelectViewModel = hiltViewModel()
) {
    /** Flag **/
    val processCode = feedSelectViewModel.processCode.observeAsState(FeedSelectCode.LOAD_FEED_LIST)

    /** Observer **/
    val feedList =
        if (code == "FD")
            feedSelectViewModel.foodList.observeAsState(ArrayList())
        else
            feedSelectViewModel.snackList.observeAsState(ArrayList())

    /** Background **/
    FeedSelectBackGround()

    /** Logic by ProcessCode **/
    when(processCode.value) {
        FeedSelectCode.LOAD_FEED_LIST -> {
            FeedSelectProcess()
        }
        else -> {
            /** Content **/
            FeedSelectContent(
                feeding = { feedCode ->
                    feedSelectViewModel.feeding(feedCode)
                    navController.popBackStack(route = NavItem.FeedNested.route, inclusive = true)
                },
                feedList = feedList
            )
        }
    }
}

@Composable
fun FeedSelectContent(
    feeding: (String) -> Unit,
    feedList: State<List<FeedModel>>
) {
    val nowIndex = remember { mutableIntStateOf(0) }
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = nowIndex.intValue
            override val pageCount: Int
                get() = feedList.value.size
        }
    }
    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            FeedItem(onClick = feeding, feedCode = feedList.value[nowIndex.intValue].foodCode)
        }
        // 화살표
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { nowIndex.intValue = max(0, nowIndex.intValue - 1) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftbnt),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Button(
                    onClick = {
                        nowIndex.intValue = min(feedList.value.size, nowIndex.intValue + 1)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rightbnt),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null
                    )
                }
            }
        }
        HorizontalPageIndicator(
            pageIndicatorState = pageIndicatorState,
            selectedColor = PaymongNavy,
            unselectedColor = Color.White,
            indicatorSize = 6.dp,
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 7.dp)
        )
    }
}