package com.paymong.wear.ui.view.infomation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Text
import com.paymong.wear.domain.viewModel.information.InformationViewModel

@Composable
fun InformationView(
    informationViewModel: InformationViewModel = hiltViewModel()
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val nameFontSize = if(screenWidthDp < 200) 20 else 24
    val ageFontSize = if(screenWidthDp < 200) 15 else 20
    val weightFontSize = if(screenWidthDp < 200) 15 else 20

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            val mongName = informationViewModel.mongName
            InformationItem(text = mongName, fontSize = nameFontSize.sp)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
        ) {
            val mongAge = informationViewModel.mongAge
            InformationItem(text = mongAge, fontSize = ageFontSize.sp)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            val mongWeight = "${informationViewModel.mongWeight}kg"
            InformationItem(text = mongWeight, fontSize = weightFontSize.sp)
        }
    }
}

@Composable
fun InformationItem(
    text: String,
    fontSize: TextUnit
) {
    Text(text = text, textAlign = TextAlign.Center, fontSize = fontSize)
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun InformationViewPreView() {
    InformationView()
}