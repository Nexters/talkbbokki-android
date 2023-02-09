package com.hammer.talkbbokki.presentation.main

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val categoryLevel by viewModel.categoryLevel.collectAsState()
    MainScreen(categoryLevel)
}

@Composable
fun MainScreen(categoryLevel: List<CategoryLevel>) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        MainHeader()
        CategoryLevels()
    }
}

@Composable
fun MainHeader() {
}

@Composable
fun CategoryLevels() {
}
