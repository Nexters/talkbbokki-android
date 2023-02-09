package com.hammer.talkbbokki.presentation.topics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.math.absoluteValue
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TopicListRoute(
    modifier: Modifier = Modifier,
    onClickToDetail: () -> Unit,
    viewModel: TopicListViewModel = hiltViewModel()
) {
    val topicList by viewModel.topicList.collectAsState()
    TopicListScreen(onClickToDetail = onClickToDetail)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicListScreen(modifier: Modifier = Modifier, onClickToDetail: () -> Unit) {
    val listState = rememberLazyListState()
    val itemWidth = with(LocalDensity.current) { 120.dp.toPx() }
    val currentIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val offset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val snappingLayout = remember(listState) { SnapLayoutInfoProvider(listState) }
    val snapFlingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val currentOffset = currentIndex.value + (offset.value / itemWidth)

    var isVisible by remember { mutableStateOf(false) }

    val cardList = (0..10).toList()
    BoxWithConstraints {
        // 대화 주제 리스트
        LazyRow(
            state = listState,
            modifier = modifier.fillMaxSize(),
            flingBehavior = snapFlingBehavior,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(
                start = ((maxWidth - 120.dp) / 2),
                end = ((maxWidth - 120.dp) / 2)
            )
        ) {
            itemsIndexed(cardList) { definiteIndex, item ->
                CardItems(definiteIndex, currentOffset)
            }
        }

/*
        // 카드 플립 애니메이션
        if (isVisible) {
            Box(modifier = modifier.align(Alignment.Center)) {
                CardAnimation()
            }
        }
*/

        Button(
            onClick = { /*isVisible = isVisible.not()*/
                onClickToDetail()
            },
            modifier = modifier.align(Alignment.BottomCenter)
        ) {
            Text("카드 선택")
        }
    }
}

@Composable
fun CardItems(definiteIndex: Int, currentOffset: Float) {
    val pageOffsetWithSign = definiteIndex - currentOffset
    val pageOffset = pageOffsetWithSign.absoluteValue
    Card(
        modifier = Modifier
            .width(120.dp)
            .aspectRatio(0.7f)
            .zIndex(5f - pageOffset)
            .graphicsLayer {
                // 중간으로 올수록 1.6배 크게 보임
                lerp(
                    start = 1f.dp,
                    stop = 1.6f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).let { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }

                // 중간으로 올수록 0도에 가까워짐. (카드 사이 각도 10도씩 틀어짐)
                lerp(
                    start = pageOffsetWithSign * 10f.dp, // -20, -10, 0, 10, 20 ...
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { angle ->
                    rotationZ = angle
                }

                // 중간에서 멀어질수록 수직으로 내려가도록 조정.
                lerp(
                    start = pageOffset * 50f.dp,
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { yOffset ->
                    translationY = yOffset
                }

                // 카드가 겹치게 보이도록 중앙으로 모이도록 조정.
                lerp(
                    start = pageOffsetWithSign * pageOffset * (50f * -1).dp,
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { xOffset ->
                    translationX = xOffset
                }
            }
    ) {
        val backgroundColor = if (pageOffset > 1.5) {
            Color(0xFF73A2F1)
        } else if (pageOffset > 0.5) {
            Color(0xFF518EF5)
        } else {
            Color.White
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            /*Image(
                painter = rememberImagePainter(
                    data = "https://avatars.githubusercontent.com/u/7722921?v=4",
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )*/
            Text(
                text = definiteIndex.toString()
            )
        }
    }
}
