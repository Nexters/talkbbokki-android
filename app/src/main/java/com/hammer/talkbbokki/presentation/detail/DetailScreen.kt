package com.hammer.talkbbokki.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    DetailScreen()
}

@Composable
fun DetailScreen(modifier: Modifier = Modifier) {
    // 카드 플립 애니메이션
    BoxWithConstraints {
        Box(
            modifier = modifier
                .align(Alignment.Center)
        ) {
            Text(text = "화면전환")
        }
    }
}
