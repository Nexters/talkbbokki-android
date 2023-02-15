package com.hammer.talkbbokki.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.MainColor02

@Composable
fun IntroRoute(
    onClickToMain: () -> Unit,
    onClickToTopicList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor02),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SplashLogo { onClickToMain() }
        /*Button(onClick = { onClickToMain() }) {
            Text(text = "메인 화면")
        }
        Button(onClick = { onClickToTopicList() }) {
            Text(text = "카드 리스트")
        }*/
    }
}

@Composable
fun SplashLogo(
    navigateToMain: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
    if (progress == 1f) {
        LaunchedEffect(Unit) { navigateToMain() }
    }
}
