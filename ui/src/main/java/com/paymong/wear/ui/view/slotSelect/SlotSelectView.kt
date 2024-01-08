package com.paymong.wear.ui.view.slotSelect

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
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.model.SlotModel
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.SlotSelectCode
import com.paymong.wear.domain.viewModel.main.SlotSelectViewModel
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view.common.background.SlotSelectBackground
import kotlin.math.max
import kotlin.math.min

@Composable
fun SlotSelectView(
    navController: NavController,
    slotSelectViewModel: SlotSelectViewModel = hiltViewModel()
) {
    /** Flag **/
    val processCode = slotSelectViewModel.processCode.observeAsState(SlotSelectCode.STAND_BY)

    /** Observer **/
    val nowSlotId = slotSelectViewModel.nowSlotId.observeAsState(DefaultValue.slotId)
    val maxSlot = slotSelectViewModel.maxSlot.observeAsState(DefaultValue.maxSlot)
    val character = slotSelectViewModel.mongModel.observeAsState(MongModel())
    val slotList = slotSelectViewModel.slotList.observeAsState(ArrayList())

    /** Background **/
    SlotSelectBackground()

    /** Logic by ProcessCode **/
    when(processCode.value) {
        SlotSelectCode.LOAD_MONG_LIST -> {
            SlotSelectProcess()
        }
        SlotSelectCode.GENERATE_MONG -> {
            SlotSelectProcess()
        }
        SlotSelectCode.REMOVE_MONG -> {
            SlotSelectProcess()
        }
        SlotSelectCode.SET_SLOT -> {
            SlotSelectProcess()
        }
        SlotSelectCode.NAVIGATE -> {
            navController.navigate(NavItem.MainNested.route) {
                popUpTo(navController.graph.id)
            }
        }
        else -> {
            /** Content **/
            SlotSelectContent(
                setSlot = { slotId ->
                    slotSelectViewModel.setSlot(slotId = slotId)
                },
                removeSlot = { slotId ->
                    slotSelectViewModel.removeSlot(slotId = slotId)
                },
                generateMong = slotSelectViewModel::generateMong,
                getMongName = { mongCode ->
                    slotSelectViewModel.getMongInfo(mongCode = mongCode)
                },
                nowSlotId = nowSlotId,
                maxSlot = maxSlot,
                character = character,
                slotList = slotList
            )
        }
    }
}

@Composable
fun SlotSelectContent(
    setSlot: (Long) -> Unit,
    removeSlot: (Long) -> Unit,
    generateMong: () -> Unit,
    getMongName: (String) -> Unit,
    nowSlotId: State<Long>,
    maxSlot: State<Int>,
    character: State<MongModel>,
    slotList: State<List<SlotModel>>
) {
    val nowIndex = remember { mutableIntStateOf(0) }
    val isSelectSlot =
        if (nowIndex.intValue < slotList.value.size)
            slotList.value[nowIndex.intValue].slotId == nowSlotId.value
        else
            false
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = nowIndex.intValue
            override val pageCount: Int
                get() = slotList.value.size + if (slotList.value.size < maxSlot.value) 1 else 0
        }
    }

    Box {
        // 생성일, 캐릭터, 버튼
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            if (slotList.value.size == nowIndex.intValue) {
                SlotAdd(onClick = {
                        generateMong()
                    }
                )
            } else {
                SlotFigure(
                    setSlot = setSlot,
                    removeSlot = { slotId ->
                        removeSlot(slotId)
                        nowIndex.intValue = nowIndex.intValue - 1
                    },
                    getMongName = getMongName,
                    isSelectSlot = isSelectSlot,
                    character = character.value,
                    mong = slotList.value[nowIndex.intValue]
                )
            }
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
                        nowIndex.intValue = min(
                            slotList.value.size + if (slotList.value.size < maxSlot.value) 0 else -1,
                            nowIndex.intValue + 1
                        )
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
