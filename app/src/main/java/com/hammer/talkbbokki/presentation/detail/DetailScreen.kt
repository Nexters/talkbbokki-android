package com.hammer.talkbbokki.presentation.detail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
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
    onClickToList: () -> Unit,
    id: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    DetailScreen(onClickToList = onClickToList, id = id)
}

@Composable
fun DetailScreen(modifier: Modifier = Modifier, onClickToList: () -> Unit, id: String) {
    var cardFace by remember { mutableStateOf(CardFace.FRONT) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .size(192.dp, 240.dp)
    ) {
        TestFlipCard(cardFace, onClickToList, id)
    }

    if (cardFace == CardFace.FRONT) {
        Button(
            onClick = {
                cardFace = CardFace.BACK
            }, modifier = modifier.wrapContentSize()
        ) {
            Text(text = "Back")
        }
    }
}

@Composable
fun TestFlipCard(cardFace: CardFace, onClickToList: () -> Unit, id: String) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(1f) }

    FlipCard(
        rotation = rotation,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(.7f)
            .graphicsLayer(
                scaleX = scale, scaleY = scale, cameraDistance = 12f
            ),
        front = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = id,
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

    if (cardFace == CardFace.FRONT) {
        LaunchedEffect(key1 = Unit) {
            coroutineScope {
                launch {
                    animate(
                        initialValue = 1f, targetValue = 1.7f, animationSpec = tween(
                            durationMillis = 1000, easing = FastOutSlowInEasing
                        )
                    ) { value: Float, _: Float ->
                        scale = value
                    }

                    animate(
                        initialValue = 0f, targetValue = 180f, animationSpec = tween(
                            durationMillis = 540, easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        rotation = value
                    }
                }
            }
        }
    } else {
        LaunchedEffect(key1 = Unit) {
            coroutineScope {
                launch {
                    animate(
                        initialValue = 180f, targetValue = 0f, animationSpec = tween(
                            durationMillis = 540, easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        rotation = value
                    }

                    animate(
                        initialValue = 1.7f, targetValue = 1f, animationSpec = tween(
                            durationMillis = 1000, easing = FastOutSlowInEasing
                        )
                    ) { value: Float, _: Float ->
                        scale = value
                    }
                    onClickToList()
                }
            }
        }
    }
}

enum class CardFace {
    FRONT, BACK
}
