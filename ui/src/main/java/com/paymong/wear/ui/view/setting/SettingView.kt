package com.paymong.wear.ui.view.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.InlineSlider
import androidx.wear.compose.material.InlineSliderDefaults
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.setting.SettingViewModel
import com.paymong.wear.ui.view.common.background.SettingBackground

@Composable
fun SettingView(
    settingViewModel: SettingViewModel = hiltViewModel()
) {

    /** Observer **/
    val sound = settingViewModel.sound.observeAsState(DefaultValue.sound)

    /** Background **/
    SettingBackground()

    /** Content **/
    SettingContent(
        sound = sound,
        setSound = { value ->
            settingViewModel.setSound(value)
        }
    )
}

@Composable
fun SettingContent(
    sound: State<Float>,
    setSound: (Float) -> Unit
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 1)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PositionIndicator(scalingLazyListState = listState)
        ScalingLazyColumn (
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
            autoCentering = AutoCenteringParams(itemIndex = 1),
            state = listState
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MusicNote,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 7.dp)
                        )
                        Text(text = "사운드 크기")
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        InlineSlider(
                            value = sound.value,
                            onValueChange = { setSound(it) },
                            increaseIcon = { Icon(InlineSliderDefaults.Increase, null) },
                            decreaseIcon = { Icon(InlineSliderDefaults.Decrease, null) },
                            valueRange = 0f..1f,
                            steps = 3,
                            segmented = true
                        )
                    }
                }
            }
        }
    }
}