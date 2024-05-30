package com.paymong.wear.ui.global.component.background

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.resource.MapResourceCode

@Composable
fun InitLandingBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "InitLandingBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MainBackground(code: String) {
    val mapResourceCode = MapResourceCode.valueOf(code).code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "MainBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SlotSelectBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "SlotSelectBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FeedMenuBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "FeedMenuBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FeedSelectBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "FeedSelectBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TrainingSelectBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "TrainingSelectBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TrainingJumpingBackground() {
    val mapResourceCode = R.drawable.training_bg

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "TrainingJumpingBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BattleLandingBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "BattleLandingBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BattleBackground() {
    val mapCode = R.drawable.battle_bg

    Image(
        painter = painterResource(mapCode),
        contentDescription = "BattleBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CollectionMenuBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "CollectionMenuBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CollectionSelectBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "CollectionSelectBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CollectionMongBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "CollectionMongBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CollectionMapBackground(code: String) {
    val mapResourceCode = MapResourceCode.valueOf(code).code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "CollectionMapBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ChargeMenuBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "FeedMenuBackground",
        contentScale = ContentScale.Crop
    )
}


@Composable
fun ChargeStarPointBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "ChargeBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ChargePayPointBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "ChargeBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MapSearchBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "MapSearchBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SettingBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "SettingBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ReferenceBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "ReferenceBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FeedbackBackground() {
    val mapResourceCode = MapResourceCode.MP000.code

    Image(
        painter = painterResource(mapResourceCode),
        contentDescription = "FeedbackBackground",
        contentScale = ContentScale.Crop
    )
}

