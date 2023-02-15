package com.hammer.talkbbokki.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.Gray04
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.MainColor01
import com.hammer.talkbbokki.ui.theme.MainColor02
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun OnBoardingRoute(
    navigateToMain: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val onBoardingList by viewModel.onBoardingList.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor02)
    ) {
        OnBoardingPager(onBoardingList) { navigateToMain() }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(
    list: List<OnBoardingInfo>,
    navigateToMain: () -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MainColor01,
            inactiveColor = Gray06,
            spacing = 4.dp,
            indicatorShape = RoundedCornerShape(8f),
            indicatorHeight = 4.dp,
            indicatorWidth = 33.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            count = list.count(),
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .height(512.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = list[it].subTitleRes),
                    style = TalkbbokkiTypography.b3_regular,
                    color = Gray04
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = list[it].titleRes),
                    style = TalkbbokkiTypography.h2_bold,
                    color = White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(54.dp))
                if (list[it].imageRes == null) {
                    OnBoardingAnimation()
                } else {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = list[it].imageRes!!),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        val buttonColor = if (pagerState.currentPage == list.lastIndex) MainColor01 else White
        val buttonTextColor = if (pagerState.currentPage == list.lastIndex) White else MainColor01
        val buttonText = if (pagerState.currentPage == list.lastIndex) R.string.onboarding_button_start else R.string.onboarding_button_next
        Text(
            modifier = Modifier
                .width(320.dp)
                .background(color = buttonColor, shape = RoundedCornerShape(8.dp))
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable {
                    if (pagerState.currentPage < list.lastIndex) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        navigateToMain()
                    }
                }
                .padding(20.dp),
            text = stringResource(id = buttonText),
            style = TalkbbokkiTypography.button_large,
            color = buttonTextColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnBoardingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.onboarding))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true
    )
    LottieAnimation(
        modifier = Modifier.fillMaxWidth(),
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun PagerIndicator(
    totalCount: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {
        items(totalCount) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalCount - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}
