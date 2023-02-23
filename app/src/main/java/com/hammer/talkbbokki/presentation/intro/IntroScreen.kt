package com.hammer.talkbbokki.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.presentation.showRewardedAd
import com.hammer.talkbbokki.ui.theme.MainColor02

@Composable
fun IntroRoute(
    navigateToOnBoarding: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel()
) {
    val showOnBoarding by viewModel.showOnBoarding.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor02),
        contentAlignment = Alignment.Center
    ) {
        SplashLogo {
            if (showOnBoarding) navigateToOnBoarding() else navigateToMain()
        }
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

@Composable
fun showAds(
    navigateToList: () -> Unit
) {
    showRewardedAd(LocalContext.current) { navigateToList() }
}
