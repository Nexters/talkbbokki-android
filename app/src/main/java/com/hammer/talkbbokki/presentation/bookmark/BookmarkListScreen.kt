package com.hammer.talkbbokki.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookMarkRoute(
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    BookMarkScreen()
}

@Composable
fun BookMarkScreen() {
}
