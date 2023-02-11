package com.hammer.talkbbokki.presentation.detail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    DetailScreen()
}

@Composable
fun DetailScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .size(192.dp, 240.dp)
    ) {
        Test()
    }

}

@Composable
fun Test() {
    var cardFace by remember { mutableStateOf(CardFace.Front) }
    var scale by remember { mutableStateOf(1f) }
    var rotationY by remember { mutableStateOf(0f) }

    FlipCard(
        cardFace = cardFace,
        onClick = { cardFace = cardFace.next },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(.7f)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationY = rotationY,
                cameraDistance = 12f
            ),
        front = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "카드",
                )
            }
        },
        back = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "대화주제",
                )
            }
        },
    )

    LaunchedEffect(key1 = Unit) {

        val transformationAnimationSpec = tween<Float>(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )

        coroutineScope {
            // 카드 더 커지기
            launch {
                animate(
                    initialValue = 1f, targetValue = 1.7f,
                    animationSpec = transformationAnimationSpec
                ) { value: Float, _: Float ->
                    scale = value
                }
            }
        }

        // 카드 뒤집기
    }
}
