package com.hammer.talkbbokki.presentation.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.hammer.talkbbokki.presentation.showInterstitial

@Composable
fun IntroRoute(
    onClickToMain: () -> Unit,
    onClickToTopicList: () -> Unit
) {
    var showAds by remember { mutableStateOf(false) }

    if (showAds) showAds { onClickToTopicList() }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { onClickToMain() }) {
                Text(text = "메인 화면")
            }
            Button(onClick = {
                showAds = !showAds
            }) {
                Text(text = "카드 리스트")
            }
        }
    }
}

@Composable
fun showAds(
    navigateToList: () -> Unit
) {
    showInterstitial(LocalContext.current) { navigateToList() }
}
