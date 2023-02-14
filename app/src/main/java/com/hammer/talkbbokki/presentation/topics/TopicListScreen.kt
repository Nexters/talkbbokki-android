package com.hammer.talkbbokki.presentation.topics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.presentation.main.CategoryLevel
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun TopicListRoute(
    modifier: Modifier = Modifier,
    onClickToDetail: (id: String) -> Unit,
    viewModel: TopicListViewModel = hiltViewModel()
) {
    val topicList by viewModel.topicList.collectAsState()
    TopicListScreen(onClickToDetail = onClickToDetail)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicListScreen(modifier: Modifier = Modifier, onClickToDetail: (id: String) -> Unit) {
    val listState = rememberLazyListState()
    val itemWidth = with(LocalDensity.current) { 120.dp.toPx() }
    val currentIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val offset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val snappingLayout = remember(listState) { SnapLayoutInfoProvider(listState) }
    val snapFlingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val currentOffset = currentIndex.value + (offset.value / itemWidth)

    val cardList = (0..10).toList()

    val level = "Level1"

    BoxWithConstraints(
        modifier = Modifier.background(CategoryLevel.valueOf(level).backgroundColor)
    ) {
        var index = ""

        Column() {
            stringResource(id = CategoryLevel.valueOf(level).title).split("\n")
                .forEachIndexed { index, s ->
                    Text(
                        text = s,
                        style = TalkbbokkiTypography.h2_bold,
                        color = if (index == 0) Color.Black else Color(0x80000000)
                    )
                }
        }

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
                index = currentOffset.roundToInt().toString()
                CardItems(definiteIndex, currentOffset)
            }
        }
        Button(
            onClick = {
                onClickToDetail(index)
            },
            modifier = modifier
                .size(335.dp, 60.dp)
                .background(Color.Black)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(8)
        ) {
            Text(text = "이 카드 뽑기")
        }
    }
}

@Composable
fun CardItems(definiteIndex: Int, currentOffset: Float) {
    val pageOffsetWithSign = definiteIndex - currentOffset
    val pageOffset = pageOffsetWithSign.absoluteValue
    Box(
        modifier = Modifier
            .width(120.dp)
            .aspectRatio(0.7f)
            .background(Color.Transparent)
            .zIndex(5f - pageOffset)
            .graphicsLayer {

                // 중간으로 올수록 크게 보임
                lerp(
                    start = if (pageOffset > 1.5) {
                        0.8f.dp
                    } else if (pageOffset > 0.5) {
                        1.0f.dp
                    } else {
                        1.2f.dp
                    },
                    stop = if (pageOffset > 1.5) {
                        1.0f.dp
                    } else if (pageOffset > 0.5) {
                        1.2f.dp
                    } else {
                        1.9f.dp
                    },
                    fraction = 1f - pageOffset.coerceIn(0f, 2f)
                ).let { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }

                // 중간으로 올수록 0도에 가까워짐. (카드 사이 각도 15도씩 틀어짐)
                lerp(
                    start = pageOffsetWithSign * 15f.dp, // -30, -15, 0, 15, 30 ...
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
        val image: Painter = if (pageOffset > 1.5) {
            painterResource(id = R.drawable.bg_card_small)
        } else if (pageOffset > 0.5) {
            painterResource(id = R.drawable.bg_card_regular)
        } else {
            painterResource(id = R.drawable.bg_card_large)
        }

        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
        Text(
            text = definiteIndex.toString()
        )
    }
}
