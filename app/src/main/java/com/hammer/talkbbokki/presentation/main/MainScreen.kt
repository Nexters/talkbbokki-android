package com.hammer.talkbbokki.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    MainScreen()
}

@Composable
fun MainScreen() {
}
