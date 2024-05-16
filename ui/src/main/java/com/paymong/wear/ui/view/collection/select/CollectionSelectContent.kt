package com.paymong.wear.ui.view.collection.select

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paymong.wear.domain.refac.vo.CollectionVo
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.LoadingBar
import com.paymong.wear.ui.global.resource.MapResourceCode
import com.paymong.wear.ui.global.resource.MongResourceCode
import com.paymong.wear.ui.view.collection.select.component.Collection
import kotlin.math.min


/** 몽/맵 컬렉션 리스트 **/
@Composable
fun CollectionSelectContent(
    code: String,
    collectionItemList: State<List<CollectionVo>>,
    isLoadingBarShow: State<Boolean>,
    isCollectionItemListLoad: State<Boolean>,
    showMongDetail: (String) -> Unit,
    showMapDetail: (String) -> Unit,
    cantShowDetail: () -> Unit,
) {
    /** 리스트 **/
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)

    if (isCollectionItemListLoad.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(vertical = 40.dp),
                state = listState,
            ) {
                for (startIndex: Int in 1..collectionItemList.value.size step (3)) {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .height(59.dp)
                        ) {
                            for (index: Int in startIndex..min(
                                collectionItemList.value.size,
                                startIndex + 2
                            )) {
                                val collectionItem = collectionItemList.value[index - 1]

                                Collection(
                                    disable = collectionItem.disable,
                                    border = R.drawable.interaction_bnt_darkpurple,
                                    backgroundColor = Color.LightGray,
                                    backgroundAlpha = 0.4f,
                                    showDetail = {
                                        if (code == "MONG") {
                                            showMongDetail(collectionItem.code)
                                        } else {
                                            showMapDetail(collectionItem.code)
                                        }
                                    },
                                    cantShowDetail = cantShowDetail,
                                    modifier = Modifier
                                        .offset(
                                            y = if (index % 3 == 2) (-27).dp else 0.dp,
                                            x = 0.dp
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = if (code == "MONG") {
                                                MongResourceCode.valueOf(collectionItem.code).pngCode
                                            } else {
                                                MapResourceCode.valueOf(collectionItem.code).code
                                            }
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .padding(if (code == "MONG") 12.dp else 0.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /** 로딩 스피너 표출 (상점 아이템 리스트 로딩 시) **/
    if (isLoadingBarShow.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = 0.4f))
                .fillMaxSize()
                .zIndex(3f)
        ) {
            LoadingBar(size = 50)
        }
    }
}
